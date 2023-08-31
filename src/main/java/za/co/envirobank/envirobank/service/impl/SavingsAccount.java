package za.co.envirobank.envirobank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.enums.TransactionType;
import za.co.envirobank.envirobank.model.entities.Account;

import java.math.BigDecimal;
import java.util.*;

import za.co.envirobank.envirobank.model.entities.AccountTypeLookup;
import za.co.envirobank.envirobank.model.entities.Transaction;
import za.co.envirobank.envirobank.respository.AccountRepository;
import za.co.envirobank.envirobank.respository.AccountTypeRepository;
import za.co.envirobank.envirobank.respository.TransactionRepository;
import za.co.envirobank.envirobank.service.AccountService;

@Service
@RequiredArgsConstructor
public class SavingsAccount implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private Account account;
    private final AccountTypeRepository accountTypeRepository;
    private AccountTypeLookup savingAccountType;
    BigDecimal balance = BigDecimal.ZERO;
    BigDecimal difference;

    @Override
    public void withdraw(String accountNum, BigDecimal amountToWithdraw) {

        //create a user “customerNum”
        UUID uuid = UUID.fromString(accountNum);

        AccountTypeLookup savingAccountType = accountTypeRepository.getAccountTypeLookupByAccountType("Savings");
        BigDecimal minimumRequired = savingAccountType.getMinimumBalance();

        Optional<Account> byId = accountRepository.findById(uuid);
        if (byId.isPresent()) {
            account = byId.get();

        } else {
            throw new NoSuchElementException("Account not found");
        }

        balance = account.getBalance();

        BigDecimal newBalance = balance.subtract(amountToWithdraw);





        if (!(amountToWithdraw.compareTo(balance) > 0) && amountToWithdraw.compareTo(balance) <= 0 &&
                (balance.subtract(amountToWithdraw).compareTo(minimumRequired) >= 0)) {
            balance = newBalance;
            account.setBalance(balance);
            System.out.println(balance);
            accountRepository.save(account);
            List<Account> accountsList = new ArrayList<>();
            accountsList.add(account);
            Transaction transaction = new Transaction(UUID.fromString("c4747da3-d010-4381-9827-66bc9c3c2b95"),amountToWithdraw,TransactionType.Withdrawal,
                    "Kota", account);
            transactionRepository.save(transaction);
            // accountRepository.updateBalanceByAccountNumber(UUID.fromString(accountNum), difference);
            // byId.get().setBalance(difference);

        } else if(minimumRequired.compareTo(balance) == 0){

            throw new NoSuchElementException("Withdrawal unavailable You balance is R1000 ");
        }else if (!(balance.subtract(amountToWithdraw).compareTo(minimumRequired) >= 0)){
            throw new NoSuchElementException("Amount exceeds balance .You can withdraw up to" +balance.subtract(minimumRequired));


        }

    }




//        if (amountToWithdraw <= account1.get().getBalance() && account1.get().getBalance()-amountToWithdraw >= 1000) {
//
//            account1.get().getBalance() -= amountToWithdraw;
//
//        } else {
//            System.out.println("Amount exceeds balance");
//        }

}

