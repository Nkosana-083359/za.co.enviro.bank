package za.co.envirobank.envirobank.transfereobject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.envirobank.envirobank.model.entities.AccountTypeLookup;
import za.co.envirobank.envirobank.model.entities.Customer;

import java.math.BigDecimal;
import java.util.UUID;
@Data

public class AccountDTO {

    private UUID accNum;


    private BigDecimal balance;


    private Customer customerId;


    private AccountTypeLookup accountTypeLookup;

}
