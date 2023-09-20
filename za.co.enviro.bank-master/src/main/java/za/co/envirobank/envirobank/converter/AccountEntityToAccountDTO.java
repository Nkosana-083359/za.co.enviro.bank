package za.co.envirobank.envirobank.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import za.co.envirobank.envirobank.model.entities.Account;
import za.co.envirobank.envirobank.transfereobject.AccountDTO;

import java.util.Objects;


@Component
@RequiredArgsConstructor
public class AccountEntityToAccountDTO implements Converter<Account, AccountDTO> {

    @Override
    public AccountDTO convert(Account source) {
        if(Objects.nonNull(source)){
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setAccNum(source.getAccNum());
            accountDTO.setBalance(source.getBalance());
            accountDTO.setAccountTypeLookup(source.getAccountTypeLookup());
            //accountDTO.setCustomerId(source.getCustomerId());
            return accountDTO;

        }


        return null;
    }
    }




