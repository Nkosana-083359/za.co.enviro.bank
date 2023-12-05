package za.co.envirobank.envirobank.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.enums.TransactionType;
import za.co.envirobank.envirobank.exceptions.AmountException;
import za.co.envirobank.envirobank.exceptions.EntityNotFoundException;
import za.co.envirobank.envirobank.exceptions.InsufficientBalanceException;
import za.co.envirobank.envirobank.exceptions.WithdrawalUnavailableException;
import za.co.envirobank.envirobank.model.entities.Account;
import za.co.envirobank.envirobank.model.entities.AccountTypeLookup;
import za.co.envirobank.envirobank.model.entities.Transaction;
import za.co.envirobank.envirobank.respository.AccountRepository;
import za.co.envirobank.envirobank.respository.AccountTypeRepository;
import za.co.envirobank.envirobank.respository.TransactionRepository;
import za.co.envirobank.envirobank.service.AccountService;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

@Service
public class AccountServiceImpl implements AccountService {
    private ModelMapper mapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Override
    public void withdraw(String accountNum, BigDecimal amountToWithdraw)
            throws WithdrawalUnavailableException, InsufficientBalanceException {
        UUID accountId = UUID.fromString(accountNum);
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            handleWithdrawal(account, amountToWithdraw);
        } else {
            throw new NoSuchElementException("Account not found");
        }
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);
        return accounts;
    }

    @Override
    public Page<Account> getAccountByCustomerId(UUID customerId, Pageable pageable) {
        Page<Account> account = accountRepository.findByCustomerId(customerId, pageable);
        if (account != null && !account.isEmpty()) {
            return account;
        }

        throw new EntityNotFoundException("User must have at least one account");
    }


    @Override
    public void transfer(String sourceAccount, BigDecimal amountToTransfer, String destinationAccount, String reference)
             {

                 UUID accountIdFrom = UUID.fromString(sourceAccount);
        UUID accountIdDestination = UUID.fromString(destinationAccount);
        Optional<Account> optionalAccountSource = accountRepository.findById(accountIdFrom);
        Optional<Account> optionalAccountDestination = accountRepository.findById(accountIdDestination);

        if (optionalAccountSource.isPresent() && optionalAccountDestination.isPresent()) {
            Account sourceAccounts = optionalAccountSource.get();
            Account destinationAccounts = optionalAccountDestination.get();

            scheduleTransfer(sourceAccounts, destinationAccounts, amountToTransfer, reference);
            executeUpdateAccount(  destinationAccounts,  amountToTransfer);
        } else {
            throw new NoSuchElementException("Account not found");
        }
    }

    @Override
    public List<Account> test() {
        return accountRepository.findAll();
    }

    @Override
    public List<Transaction> getTransaction(String savingsCustomerId) {
        List<Transaction> fromSavings = transactionRepository.findTransactionsFromSavingsAccount(savingsCustomerId);
        List<Transaction> toSavings = transactionRepository.findTransactionsToSavingsAccount(savingsCustomerId);
        List<Transaction> fromCurrent = transactionRepository.findTransactionsFromCurrentAccount(savingsCustomerId);
        List<Transaction> toCurrent = transactionRepository.findTransactionsToCurrentAccount(savingsCustomerId);
//        // Combine and return both lists or perform necessary operations
//         //return List<Transaction> savingsTransactions =
        List<Transaction> transactionSavings = Stream.concat(fromSavings.stream(), toSavings.stream()).collect(Collectors.toList());
        List<Transaction> transactionCurrent = Stream.concat(fromCurrent.stream(), toSavings.stream()).collect(Collectors.toList());
        //Stream.concat(transactionSavings, transactionCurrent).collect(Collectors.toList());
        return transactionSavings;
    }


    private void handleWithdrawal(Account account, BigDecimal amountToWithdraw)
            throws WithdrawalUnavailableException, InsufficientBalanceException {
        AccountTypeLookup typeLookup = account.getAccountTypeLookup();

        Long accountTypeId = typeLookup.getAccount_type_id();

        Optional<AccountTypeLookup> savingAccountType = accountTypeRepository.findById(accountTypeId);

        AccountTypeLookup accountType = savingAccountType.get();

        BigDecimal minimumRequired = accountType.getMinimumBalance();
        BigDecimal overdraft = accountType.getOverdraftLimit();
        BigDecimal currentBalance = account.getBalance();
        String accountTypeString = accountType.getAccountType();

        if (isCurrentAccountWithdrawalValid(amountToWithdraw, currentBalance, overdraft, accountTypeString) ||
                isSavingAccountWithdrawalValid(amountToWithdraw, currentBalance, minimumRequired, accountTypeString)) {
            BigDecimal newBalance = currentBalance.subtract(amountToWithdraw);
            account.setBalance(newBalance);
            accountRepository.save(account);
        } else if (minimumRequired.compareTo(currentBalance) == 0) {
            throw new WithdrawalUnavailableException("Withdrawal unavailable. Your balance is " + minimumRequired);
        } else {
            BigDecimal maxWithdrawalAmount = currentBalance.subtract(minimumRequired);
            throw new InsufficientBalanceException("Amount exceeds balance. You can withdraw up to " + maxWithdrawalAmount);
        }
    }

    private boolean isSavingAccountWithdrawalValid(BigDecimal amountToWithdraw, BigDecimal currentBalance,
                                                   BigDecimal minimumRequired, String accountTypeString) {
        BigDecimal newBalance = currentBalance.subtract(amountToWithdraw);
        return amountToWithdraw.compareTo(currentBalance) <= 0 &&
                newBalance.compareTo(minimumRequired) >= 0 && accountTypeString.equals("Savings");
    }

    private boolean isCurrentAccountWithdrawalValid(BigDecimal amountToWithdraw, BigDecimal currentBalance,
                                                    BigDecimal overdraft, String accountTypeString) {
        BigDecimal maxWithdrawal = currentBalance.add(overdraft);

        return amountToWithdraw.compareTo(maxWithdrawal) <= 0 && accountTypeString.equals("Current");
    }
    private void scheduleTransfer(Account sourceAccount, Account destinationAccount, BigDecimal amountToTransfer, String reference) {
        // Implement a scheduler to execute the fund transfer after 1 minute

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(() -> {
            // Execute the fund transfer after 1 minute
            handleTransfer(sourceAccount, destinationAccount, amountToTransfer, reference);
        }, 5, TimeUnit.SECONDS);

        // Shutdown the executor after the task is done
        executorService.shutdown();
    }
    private void handleTransfer(Account sourceAccount, Account destinationAccount, BigDecimal amountToTransfer, String reference)
            {
        AccountTypeLookup sourceAccountType = sourceAccount.getAccountTypeLookup();
        AccountTypeLookup destinationAccountType = destinationAccount.getAccountTypeLookup();

        BigDecimal sourceBalance = sourceAccount.getBalance();
        BigDecimal minimumRequiredSource = sourceAccountType.getMinimumBalance();
        BigDecimal overdraftSource = sourceAccountType.getOverdraftLimit();

        BigDecimal destinationBalance = destinationAccount.getBalance();
        BigDecimal minimumRequiredDestination = destinationAccountType.getMinimumBalance();

        if (isTransferValid(amountToTransfer, sourceBalance, minimumRequiredSource, overdraftSource, destinationBalance, minimumRequiredDestination,sourceAccountType.getAccountType(),destinationAccountType.getAccountType())) {
            BigDecimal newSourceBalance = sourceBalance.subtract(amountToTransfer);
            sourceAccount.setBalance(newSourceBalance);
            accountRepository.save(sourceAccount);

            BigDecimal newDestinationBalance = destinationBalance.add(amountToTransfer);
            destinationAccount.setBalance(newDestinationBalance);
            accountRepository.save(destinationAccount);
            // Record transactions for both accounts
            // recordTransaction(sourceAccount, amountToTransfer, TransactionType.Transfer, "Kota", destinationAccount, true);
            // recordTransaction(destinationAccount, amountToTransfer, TransactionType.Deposit, "Kota", sourceAccount, true);
            Transaction transaction = new Transaction(UUID.randomUUID(), amountToTransfer, TransactionType.Transfer, reference, sourceAccount, destinationAccount, OffsetDateTime.now(), true);
            transactionRepository.save(transaction);
            Transaction destinationTransaction = new Transaction(UUID.randomUUID(), amountToTransfer, TransactionType.Transfer, reference, destinationAccount, sourceAccount, OffsetDateTime.now(), true);
            transactionRepository.save(transaction);
            transactionRepository.save(destinationTransaction);
        } else if (minimumRequiredSource.compareTo(sourceBalance) == 0) {
            throw new AmountException("Transfer unavailable from the source account. The balance is below the minimum required.");
        } else {
            throw new AmountException("Transfer amount exceeds the available balance in the source account.");
        }
    }

    private boolean isTransferValid(BigDecimal amountToTransfer, BigDecimal sourceBalance, BigDecimal minimumRequiredSource,
                                    BigDecimal overdraftSource, BigDecimal destinationBalance, BigDecimal minimumRequiredDestination, String sourceAccountType, String destinationAccountType) {
        BigDecimal newSourceBalance = sourceBalance.subtract(amountToTransfer);

        boolean sourceAccountValid = isWithdrawalValid(amountToTransfer, sourceBalance, minimumRequiredSource, overdraftSource, sourceAccountType);
//        boolean destinationAccountValid = isDepositValid(amountToTransfer, destinationBalance, minimumRequiredDestination, destinationAccountType);

        return sourceAccountValid;
    }
    private void executeUpdateAccount( Account destinationAccount, BigDecimal amountToTransfer) {

        BigDecimal destinationBalance = destinationAccount.getAvailableBalance();
        BigDecimal newDestinationBalance = destinationBalance.add(amountToTransfer);
        destinationAccount.setAvailableBalance(newDestinationBalance);
        accountRepository.save(destinationAccount);

    }

    private void executeUpdateSourceAccount( Account destinationAccount, BigDecimal amountToTransfer) {

        BigDecimal destinationBalance = destinationAccount.getAvailableBalance();
        BigDecimal newDestinationBalance = destinationBalance.add(amountToTransfer);
        destinationAccount.setAvailableBalance(newDestinationBalance);
        accountRepository.save(destinationAccount);

    }

    private boolean isWithdrawalValid(BigDecimal amountToWithdraw, BigDecimal currentBalance, BigDecimal minimumRequired,
                                      BigDecimal overdraft, String accountTypeString) {
        BigDecimal maxWithdrawal = currentBalance.add(overdraft);

        if (accountTypeString.equals("Current")) {
            return amountToWithdraw.compareTo(maxWithdrawal) <= 0;
        } else if (accountTypeString.equals("Savings")) {
            BigDecimal newBalance = currentBalance.subtract(amountToWithdraw);
            return amountToWithdraw.compareTo(currentBalance) <= 0 && newBalance.compareTo(minimumRequired) >= 0;
        }

        return false;
    }

    public void softDelete(UUID id) throws EntityNotFoundException {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setActive(false);
            transactionRepository.save(transaction);
        } else {
            throw new EntityNotFoundException("Transaction not found.");
        }
    }
}
