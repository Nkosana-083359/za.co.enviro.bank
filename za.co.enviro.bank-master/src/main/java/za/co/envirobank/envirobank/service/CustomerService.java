package za.co.envirobank.envirobank.service;

import jakarta.mail.MessagingException;
import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.transfereobject.CustomerRequest;
import za.co.envirobank.envirobank.transfereobject.CustomerResponse;
import za.co.envirobank.envirobank.transfereobject.LoginRequest;

import java.util.UUID;

public interface CustomerService {

    CustomerResponse registerCustomer(CustomerRequest customer) throws Exception;

    CustomerResponse login(LoginRequest loginRequest);
    void updateCustomerDetails(UUID id, CustomerRequest customer);
    Customer getCustomerByCustomerId(UUID id);

}
