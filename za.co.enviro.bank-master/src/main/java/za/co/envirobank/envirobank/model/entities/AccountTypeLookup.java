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

}
