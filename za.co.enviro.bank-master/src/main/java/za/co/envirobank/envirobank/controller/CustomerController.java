package za.co.envirobank.envirobank.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.envirobank.envirobank.converter.CustomerRequestToCustomerEntity;
import za.co.envirobank.envirobank.exceptions.EntityNotFoundException;
import za.co.envirobank.envirobank.model.entities.Account;
import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.service.CustomerService;
import za.co.envirobank.envirobank.service.impl.AccountServiceImpl;
import za.co.envirobank.envirobank.service.impl.CustomerServiceImpl;
import za.co.envirobank.envirobank.service.impl.UserServiceImpl;
import za.co.envirobank.envirobank.transfereobject.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/customer/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRequestToCustomerEntity customerRequestToCustomerEntity;
    private final CustomerServiceImpl service;
    private final AccountServiceImpl accountService;
    private final CustomerServiceImpl service1;
    private final CustomerService service0;
    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;
    @PermitAll
    @PostMapping(path = "register",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserAuthenticationResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(service.registerCustomer(customerRequest));
    }

    @PermitAll
    @PutMapping(path = "update",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateCustomerDetails(@Valid @RequestBody CustomerRequest customer) {
        service.updateCustomerDetails(customer.getIdNum(), customer);
        return ResponseEntity.ok("Customer Details Updated");
    }

    @PermitAll
    @GetMapping("/accounts")
    public List<?> getAccounts(Pageable pageable, @RequestParam(name = "customerId", required = false) UUID customerId) {
        if (customerId == null) {
            return userService.getUsersWithAccountDetailsAndWithoutAccounts().stream()
                    .map(user -> modelMapper.map(user, AccountDetailsResponse.class)).toList();
        } else {
            Customer customer = service.getAllCustomerById(customerId);
            return accountService.getAccountByCustomerId(customer.getId(), pageable).getContent().stream()
                    .map(account -> modelMapper.map(account, AccountDetailsResponse.class)).toList();
        }
    }

    @PermitAll
    @GetMapping("/transactions")
    public List<?> getAccountTransactions(@RequestParam(name = "accNum") String customerId) {
        List<TransactionDetails> response = accountService.getTransaction(customerId).stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDetails.class)).toList();

        return response;
    }

    @PermitAll
    @GetMapping("/customer")
    public List<AccountDetailsResponse> getCustomerAll(@RequestParam(name = "customerId", required = false) String customerId, Pageable pageable) {
        if (customerId == null) {
            return userService.getAllUser().stream()
                    .map(user -> modelMapper.map(user, AccountDetailsResponse.class)).toList();
        } else {
            Customer customer = service.getByIdNum(customerId);
            Page<Account> userAccounts = accountService.getAccountByCustomerId(customer.getId(), pageable);
            if (Objects.nonNull(userAccounts)) {
                List<AccountDetailsResponse> accountDetailsResponses = userAccounts.stream()
                        .map(account -> modelMapper.map(account, AccountDetailsResponse.class))
                        .collect(Collectors.toList());
                return accountDetailsResponses;
            }
            throw new EntityNotFoundException("User must have at least one account");
        }
    }

    @PermitAll
    @GetMapping("/customers")
    public CustomerResponse getCustomer(@RequestParam(name = "customerId", required = false) String customerId, Pageable pageable) {
        Customer customer = service.getByIdNum(customerId);

        if (Objects.nonNull(customer)) {
            CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
            return customerResponse;
        }

        throw new EntityNotFoundException("User must have at least one account");
    }


}
