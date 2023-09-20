package za.co.envirobank.envirobank.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.envirobank.envirobank.model.entities.Transaction;

import java.util.UUID;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
