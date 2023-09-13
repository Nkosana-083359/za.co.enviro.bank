package za.co.envirobank.envirobank.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.respository.CustomerRepository;
import za.co.envirobank.envirobank.service.CustomerService;
import za.co.envirobank.envirobank.transfereobject.CustomerRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public void registerCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomerDetails(Integer id, CustomerRequest customerRequest) {
       Customer customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer Does not exist"));

        customer.setIdNumber(customerRequest.getIdNum());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setName(customerRequest.getName());
        customer.setSurname(customerRequest.getSurname());
        customerRepository.save(customer);

     }

    @Override
    public Customer getCustomerByCustomerId(Integer id) {

        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer Does not exist"));
    }
}
