package za.co.envirobank.envirobank.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public interface AccountService {

     void withdraw (String accountNum, BigDecimal amountToWithdraw);

}
