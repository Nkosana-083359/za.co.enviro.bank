package za.co.envirobank.envirobank.transfereobject;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustomerDetailsResponse {

    private String customerName;
    private String customerSurname;
    private String customerEmail;
    private String customerPhoneNumber;
    private String customerIdNumber;
    private String customerUsername;
    private BigDecimal accountsBalance;
    private BigDecimal accountsAvailableBalance;
    private String accountsAccountTypeLookupAccountType;


}