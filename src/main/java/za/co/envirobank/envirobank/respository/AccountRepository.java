package za.co.envirobank.envirobank.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.envirobank.envirobank.model.entities.Account;

import java.math.BigDecimal;
import java.util.UUID;
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {


    //void updateBalanceByAccountNumber(UUID accountNum, BigDecimal newBalance);


}