package za.co.envirobank.envirobank.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.co.envirobank.envirobank.converter.CustomerRequestToCustomerEntity;
import za.co.envirobank.envirobank.model.entities.Account;
import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.service.EmailService;
import za.co.envirobank.envirobank.service.impl.AccountServiceImpl;
import za.co.envirobank.envirobank.service.impl.CustomerServiceImpl;
import za.co.envirobank.envirobank.transfereobject.AccountsResponse;
import za.co.envirobank.envirobank.transfereobject.CustomerRequest;
import za.co.envirobank.envirobank.transfereobject.CustomerResponse;
import za.co.envirobank.envirobank.utils.AppConstants;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/customer/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRequestToCustomerEntity customerRequestToCustomerEntity;
    private final CustomerServiceImpl service;
    private final AccountServiceImpl accountService;
    private final CustomerServiceImpl service1;


   @PreAuthorize(value = "hasRole({'ADMIN_ROLE'})")
    @PostMapping("register")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) throws Exception {
        //Customer customer1= customerRequestToCustomerEntity.convert(customer);

        return ResponseEntity.ok(service.registerCustomer(customerRequest));

    }
    @PreAuthorize(value = "hasRole({'ADMIN_ROLE'})")
    @PutMapping("update/{id}")
    public ResponseEntity<String> updateCustomerDetails(@PathVariable UUID id, @Valid @RequestBody CustomerRequest customer){
        service.updateCustomerDetails(id,customer);
        return ResponseEntity.ok("Customer Details Updated");
    }

    @PreAuthorize(value = "hasRole({'ADMIN_ROLE'})")
    @PostMapping("/accounts")
    public AccountsResponse getAccounts(
            @RequestParam(value = "pageNumber", defaultValue = "0",required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(name = "customerId",required = false)UUID customerId){

        if(customerId == null){
            return accountService.findAll( pageNo,pageSize,sortBy,sortDir );
        }else{Customer customer = service.getCustomerByCustomerId(customerId);
            List<Account> account = accountService.getAccountByCustomerId(customer);
            return (AccountsResponse) account;
        }

    }

}
