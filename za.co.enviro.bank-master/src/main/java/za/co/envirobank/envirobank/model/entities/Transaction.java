package za.co.envirobank.envirobank.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.envirobank.envirobank.enums.AccountType;
import za.co.envirobank.envirobank.enums.TransactionType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bankTransactions")
public class Transaction {
    @Id
    private UUID transactionId;

    private BigDecimal transactionAmount;
    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    private String reference;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sourceAccNumber")
    private Account accounts;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "destinationaccnumber")
    private Account destinationAccNumber;

    @Column(name = "creation_date")
    private OffsetDateTime creationDate;

    private Boolean active=true;

}
