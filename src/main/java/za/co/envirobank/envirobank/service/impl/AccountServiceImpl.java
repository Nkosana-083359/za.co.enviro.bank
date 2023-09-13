package za.co.envirobank.envirobank.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.enums.TransactionType;
import za.co.envirobank.envirobank.exceptions.EntityNotFoundException;
import za.co.envirobank.envirobank.exceptions.InsufficientBalanceException;
import za.co.envirobank.envirobank.exceptions.WithdrawalUnavailableException;
import za.co.envirobank.envirobank.model.entities.Account;

import java.math.BigDecimal;
import java.util.*;

import za.co.envirobank.envirobank.model.entities.AccountTypeLookup;
import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.model.entities.Transaction;
import za.co.envirobank.envirobank.respository.AccountRepository;
import za.co.envirobank.envirobank.respository.AccountTypeRepository;
import za.co.envirobank.envirobank.respository.TransactionRepository;
import za.co.envirobank.envirobank.service.AccountService;
import za.co.envirobank.envirobank.transfereobject.AccountsResponse;

@Service

public class AccountServiceImpl implements AccountService {
    private ModelMapper mapper;
    @Autowired
    private  AccountRepository accountRepository;
    @Autowired
    private  TransactionRepository transactionRepository;
    @Autowired

    private  AccountTypeRepository accountTypeRepository;

    BigDecimal balance = BigDecimal.ZERO;
    BigDecimal difference;

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
    public AccountsResponse findAll(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Account> accounts = accountRepository.findAll(pageable);

        List<Account> content = accounts.getContent();
        //List<Account> content = accountList;
        //accountList.stream().toList().stream().map(account -> )
        //remove the transfer object
        AccountsResponse accountsResponse = new AccountsResponse();
        accountsResponse.setContent(content);
        accountsResponse.setPageNo(accounts.getNumber());
        accountsResponse.getPageSize();
        accountsResponse.setTotalElements(accounts.getTotalElements());
        accountsResponse.setTotalPages(accounts.getTotalPages());
        accountsResponse.setLast(accounts.isLast());

        return accountsResponse;
    }

    @Override
    public List<Account> getAccountByCustomerId(Customer customerId) {
        List<Account> account = accountRepository.findAccountByCustomer(customerId);
        return account;
    }

    private void handleWithdrawal(Account account, BigDecimal amountToWithdraw)
            throws WithdrawalUnavailableException, InsufficientBalanceException {
        //
        AccountTypeLookup typeLookup = account.getAccountTypeLookup();

        Long accountTypeId = typeLookup.getAccount_type_id();

        Optional<AccountTypeLookup> savingAccountType = accountTypeRepository.findById(accountTypeId);

        AccountTypeLookup accountType = savingAccountType.get();

        BigDecimal minimumRequired = accountType.getMinimumBalance();
        BigDecimal overdraft = accountType.getOverdraftLimit();
        BigDecimal currentBalance = account.getBalance();
        String accountTypeString = accountType.getAccountType();


        if (isCurrentAccountWithdrawalValid(amountToWithdraw, currentBalance, overdraft,accountTypeString) ||
                isSavingAccountWithdrawalValid(amountToWithdraw, currentBalance, minimumRequired,accountTypeString)) {
            BigDecimal newBalance = currentBalance.subtract(amountToWithdraw);
            account.setBalance(newBalance);
            accountRepository.save(account);

            Transaction transaction = new Transaction(UUID.randomUUID(), amountToWithdraw, TransactionType.Withdrawal,
                    "Kota", account,true);            transactionRepository.save(transaction);
        } else if (minimumRequired.compareTo(currentBalance) == 0) {

           throw new WithdrawalUnavailableException("Withdrawal unavailable. Your balance is R" + minimumRequired);
        } else {

            BigDecimal maxWithdrawalAmount = currentBalance.subtract(minimumRequired);
          throw new InsufficientBalanceException("Amount exceeds balance. You can withdraw up to " + maxWithdrawalAmount);
        }
    }


    private boolean isSavingAccountWithdrawalValid(BigDecimal amountToWithdraw, BigDecimal currentBalance,
                                                   BigDecimal minimumRequired,String accountTypeString) {
        BigDecimal newBalance = currentBalance.subtract(amountToWithdraw);
        return amountToWithdraw.compareTo(currentBalance) <= 0 &&
                newBalance.compareTo(minimumRequired) >= 0 && accountTypeString.equals("Savings");
    }

    private boolean isCurrentAccountWithdrawalValid(BigDecimal amountToWithdraw, BigDecimal currentBalance,
                                                    BigDecimal overdraft,String accountTypeString) {
        BigDecimal maxWithdrawal = currentBalance.add(overdraft);
       //BigDecimal withDrawableAmount = currentBalance.subtract(amountToWithdraw);

        return amountToWithdraw.compareTo(maxWithdrawal) <= 0 && accountTypeString.equals("Current");
    }

    public void softDelete(UUID id) throws EntityNotFoundException {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if(optionalTransaction.isPresent()){
            Transaction transaction = optionalTransaction.orElseThrow();
            transaction.setActive(false);
            transactionRepository.save(transaction);

        } else{
            throw new EntityNotFoundException("Transaction no found.");
        }
    }





//    @Override
//    public void withdraw(String accountNum, BigDecimal amountToWithdraw) {
//
//        //create a user “customerNum”
//        UUID uuid = UUID.fromString(accountNum);
//
//        AccountTypeLookup savingAccountType = accountTypeRepository.getAccountTypeLookupByAccountType("Savings");
//        BigDecimal minimumRequired = savingAccountType.getMinimumBalance();
//
//        Optional<Account> byId = accountRepository.findById(uuid);
//        if (byId.isPresent()) {
//            account = byId.get();
//
//        } else {
//                throw new NoSuchElementException("Account not found");
//        }
//
//        balance = account.getBalance();
//        BigDecimal newBalance = balance.subtract(amountToWithdraw);
//
//
//
//
//
//        if (!(amountToWithdraw.compareTo(balance) > 0) && amountToWithdraw.compareTo(balance) <= 0 &&
//                (balance.subtract(amountToWithdraw).compareTo(minimumRequired) >= 0)) {
//            balance = newBalance;
//            account.setBalance(balance);
//            System.out.println(balance);
//            accountRepository.save(account);
//            List<Account> accountsList = new ArrayList<>();
//            accountsList.add(account);
//            Transaction transaction = new Transaction(UUID.fromString("c4747da3-d010-4381-9827-66bc9c3c2b95"),amountToWithdraw,TransactionType.Withdrawal,
//                    "Kota", account);
//            transactionRepository.save(transaction);
//            // accountRepository.updateBalanceByAccountNumber(UUID.fromString(accountNum), difference);
//            // byId.get().setBalance(difference);
//
//        } else if(minimumRequired.compareTo(balance) == 0){
//
//            throw new NoSuchElementException("Withdrawal unavailable You balance is R1000 ");
//        }else if (!(balance.subtract(amountToWithdraw).compareTo(minimumRequired) >= 0)){
//            throw new NoSuchElementException("Amount exceeds balance .You can withdraw up to" +balance.subtract(minimumRequired));
//
//
//        }

    }







