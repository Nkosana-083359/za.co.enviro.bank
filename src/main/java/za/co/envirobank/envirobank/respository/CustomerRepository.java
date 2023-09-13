package za.co.envirobank.envirobank.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.envirobank.envirobank.model.entities.Customer;


public interface CustomerRepository extends JpaRepository<Customer,Integer> {

}
