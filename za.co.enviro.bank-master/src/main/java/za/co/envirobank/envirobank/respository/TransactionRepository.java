package za.co.envirobank.envirobank.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.envirobank.envirobank.model.entities.Transaction;

import java.util.List;
import java.util.UUID;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    // Retrieve transactions where savings account is the source
    @Query("SELECT t FROM Transaction t WHERE t.accounts.customer.idNumber = :savingsCustomerId")
    List<Transaction> findTransactionsFromSavingsAccount(@Param("savingsCustomerId") String savingsCustomerId);

    // Retrieve transactions where savings account is the destination
    @Query("SELECT t FROM Transaction t WHERE t.destinationAccNumber.customer.idNumber = :savingsCustomerId")
    List<Transaction> findTransactionsToSavingsAccount(@Param("savingsCustomerId") String savingsCustomerId);

    // Retrieve transactions where current account is the source
    @Query("SELECT t FROM Transaction t WHERE t.accounts.customer.idNumber = :currentCustomerId")
    List<Transaction> findTransactionsFromCurrentAccount(@Param("currentCustomerId") String currentCustomerId);

    // Retrieve transactions where current account is the destination
    @Query("SELECT t FROM Transaction t WHERE t.destinationAccNumber.customer.idNumber = :currentCustomerId")
    List<Transaction> findTransactionsToCurrentAccount(@Param("currentCustomerId") String currentCustomerId);
}

