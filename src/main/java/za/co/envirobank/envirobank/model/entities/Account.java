package za.co.envirobank.envirobank.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.envirobank.envirobank.enums.AccountType;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="bank_accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "acc_num")
    private UUID accNum;

    @Column(name = "acc_balance")
    private BigDecimal balance;

    private Integer customerNum;

    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "accountTypeId")
    private AccountTypeLookup accountTypeLookup;


}
