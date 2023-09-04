package za.co.envirobank.envirobank.service;

import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.exceptions.InsufficientBalanceException;
import za.co.envirobank.envirobank.exceptions.WithdrawalUnavailableException;

import java.math.BigDecimal;
@Service
public interface AccountService {

     void withdraw (String accountNum, BigDecimal amountToWithdraw) throws WithdrawalUnavailableException, InsufficientBalanceException;

}
