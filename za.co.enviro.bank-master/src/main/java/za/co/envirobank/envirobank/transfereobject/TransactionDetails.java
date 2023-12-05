package za.co.envirobank.envirobank.transfereobject;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
@Getter
@Setter
public class TransactionDetails {

    private String transactionAmount;
    private String transactionType;
    private String reference;
    private BigDecimal accountsBalance;
    private BigDecimal accountsAvailableBalance;
    private String accountsCustomerPhoneNumber;
    private String accountsAccountTypeLookupAccountType;
    private String accountsCustomerName;
    private String accountsCustomerSurname;
    private String accountsCustomerId;
    private String accountsCustomerEmail;
    private OffsetDateTime creationDate;
    private String destinationAccNumberAccountTypeLookupAccountType;

}
