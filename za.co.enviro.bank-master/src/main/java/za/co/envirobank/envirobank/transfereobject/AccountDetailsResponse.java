package za.co.envirobank.envirobank.transfereobject;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;
@Getter
@Setter
public class AccountDetailsResponse {

    private UUID accNum;
    private BigDecimal balance;
    private BigDecimal availableBalance;
    private String accountTypeLookupAccountType;
    private String customerName;

    private String customerSurname;
    private String customerIdNumber;
    private String customerEmail;
    private String customerPhoneNumber;
    private BigInteger customerNumAccounts;




}
