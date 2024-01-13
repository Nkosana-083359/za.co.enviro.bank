package za.co.envirobank.envirobank.service;

import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.transfereobject.CustomerRequest;

public interface CustomerService {

    void registerCustomer(Customer customer);
    void updateCustomerDetails(Integer id, CustomerRequest customer);
    Customer getCustomerByCustomerId(Integer id);
}
