package za.co.envirobank.envirobank.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor

@AllArgsConstructor
public class AccountTypeLookup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_type_id")
    private Long account_type_id;

    private String accountType;

    private BigDecimal minimumBalance;

    private BigDecimal overdraftLimit;



//    CREATE TABLE IF NOT EXISTS account_type_lookup (
//            acc_num UUID PRIMARY KEY DEFAULT gen_random_uuid(),
//    account_type VARCHAR(255) NOT NULL,
//    minimum_balance DECIMAL(10, 2) NOT NULL,
//    overdraft_limit DECIMAL(10, 2) NOT NULL
//    );

}
