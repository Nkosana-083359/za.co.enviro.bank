package za.co.envirobank.envirobank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import za.co.envirobank.envirobank.model.CustomerRequest;
import za.co.envirobank.envirobank.model.entities.Customer;

@Controller
@RequestMapping("v1/api/customer")
public class CustomerController {

    @PostMapping
    public void createCustomer(@RequestBody CustomerRequest customer){


    }

}
