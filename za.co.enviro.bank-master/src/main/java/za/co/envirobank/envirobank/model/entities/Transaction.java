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
    @JoinColumn(name = "accNumber")
    private Account accounts;

    private Boolean active=true;
//
//    CREATE TABLE IF NOT EXISTS bank_accounts (
//            acc_num UUID PRIMARY KEY DEFAULT gen_random_uuid(),
//    acc_balance NUMERIC ,
//    acc_type VARCHAR(50) NOT NULL
//);
//
//    CREATE TABLE IF NOT EXISTS bank_transactions (
//            trans_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
//    trans_type NUMERIC ,
//    reference VARCHAR(50),
//    acc_number UUID,
//    CONSTRAINT foreign_key_acc_number FOREIGN KEY(acc_number) REFERENCES bank_accounts(acc_num) ON UPDATE CASCADE ON DELETE SET NULL
//);
//, referencedColumnName = "acc_num"
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "news_version_id", referencedColumnName = "news_version_id")
//    private NewsVersionEntity newsVersion;


}
