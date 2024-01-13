package za.co.envirobank.envirobank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.envirobank.envirobank.converter.CustomerRequestToCustomerEntity;
import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.service.impl.CustomerServiceImpl;
import za.co.envirobank.envirobank.transfereobject.CustomerRequest;

@RestController
@RequestMapping(path = "/api/customer/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRequestToCustomerEntity customerRequestToCustomerEntity;
    private final CustomerServiceImpl service;



    @PostMapping("register")
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerRequest customer){
        Customer customer1= customerRequestToCustomerEntity.convert(customer);
        service.registerCustomer(customer1);
        return ResponseEntity.ok("User Registered Successfully");

    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateCustomerDetails(@PathVariable Integer id,@Valid @RequestBody CustomerRequest customer){
        service.updateCustomerDetails(id,customer);
        return ResponseEntity.ok("Customer Details Updated");
    }

}
