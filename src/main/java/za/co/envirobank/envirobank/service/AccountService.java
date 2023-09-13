package za.co.envirobank.envirobank.service;

import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.exceptions.InsufficientBalanceException;
import za.co.envirobank.envirobank.exceptions.WithdrawalUnavailableException;
import za.co.envirobank.envirobank.model.entities.Account;
import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.transfereobject.AccountsResponse;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface AccountService {

     void withdraw (String accountNum, BigDecimal amountToWithdraw) throws WithdrawalUnavailableException, InsufficientBalanceException;
     AccountsResponse findAll(int pageNo, int pageSize,String sortBy,String sortDir);

     List<Account> getAccountByCustomerId(Customer customerId);

//    Account getAccountByCustomerId(UUID id);
}
