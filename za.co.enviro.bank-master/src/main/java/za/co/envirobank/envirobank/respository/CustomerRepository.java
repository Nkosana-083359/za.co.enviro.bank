package za.co.envirobank.envirobank.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.envirobank.envirobank.model.entities.Customer;

import java.util.UUID;


public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
