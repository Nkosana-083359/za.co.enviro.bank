package za.co.envirobank.envirobank.service;

import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.transfereobject.*;

import java.util.UUID;

public interface CustomerService {

    UserAuthenticationResponse registerCustomer(CustomerRequest customer) throws Exception;

    UserAuthenticationResponse login(LoginRequest loginRequest) ;
    void updateCustomerDetails(String id, CustomerRequest customer);
    Customer getAllCustomerById(UUID id);
    Customer getByIdNum(String idNum);

}
