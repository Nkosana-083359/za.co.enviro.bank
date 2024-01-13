package za.co.envirobank.envirobank.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.envirobank.envirobank.model.entities.Account;
import za.co.envirobank.envirobank.model.entities.Customer;

import java.util.List;
import java.util.UUID;
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findAccountByCustomer(Customer customerId);
    //void updateBalanceByAccountNumber(UUID accountNum, BigDecimal newBalance);
        //Account getAccountById(UUID id);

}
