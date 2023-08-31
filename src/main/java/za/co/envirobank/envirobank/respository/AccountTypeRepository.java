package za.co.envirobank.envirobank.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.envirobank.envirobank.model.entities.AccountTypeLookup;

import java.util.UUID;

public interface AccountTypeRepository extends JpaRepository<AccountTypeLookup, UUID> {
        AccountTypeLookup getAccountTypeLookupByAccountType(String accountType);
}
