package za.co.envirobank.envirobank.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.exceptions.InsufficientBalanceException;
import za.co.envirobank.envirobank.exceptions.WithdrawalUnavailableException;
import za.co.envirobank.envirobank.model.entities.Account;
import za.co.envirobank.envirobank.model.entities.Transaction;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public interface AccountService {

     void withdraw (String accountNum, BigDecimal amountToWithdraw) throws WithdrawalUnavailableException, InsufficientBalanceException;
     Page<Account> findAll(Pageable pageable);

     Page<Account> getAccountByCustomerId(UUID customerId,Pageable pageable);

     void transfer (String accountNumberFrom, BigDecimal amountToWithdraw,String accountNumberTo,String reference);

      List<Account> test();
      List<Transaction>  getTransaction(String id);
}
