package za.co.envirobank.envirobank.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.envirobank.envirobank.model.entities.Account;

import java.util.List;
import java.util.UUID;
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Page<Account> findByCustomerId(UUID customerId, Pageable pageable);
    List<Account> findByCustomerId(UUID customerId);

}
