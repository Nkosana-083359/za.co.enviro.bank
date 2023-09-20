package za.co.envirobank.envirobank.service.impl;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.model.entities.Role;
import za.co.envirobank.envirobank.respository.CustomerRepository;
import za.co.envirobank.envirobank.respository.RoleRepository;
import za.co.envirobank.envirobank.service.CustomerService;
import za.co.envirobank.envirobank.service.EmailService;
import za.co.envirobank.envirobank.service.UserService;
import za.co.envirobank.envirobank.service.za.id.validator.IDNumberParser;
import za.co.envirobank.envirobank.service.za.id.validator.IDNumberData;
import za.co.envirobank.envirobank.transfereobject.CustomerRequest;
import za.co.envirobank.envirobank.transfereobject.CustomerResponse;
import za.co.envirobank.envirobank.transfereobject.LoginRequest;
import za.co.envirobank.envirobank.utils.JwtSecurityUtil;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private final JwtSecurityUtil jwtSecurityUtil;
    private EmailService emailService;
    private final  IDNumberParser idNumberParser;


    @Override
    public CustomerResponse registerCustomer(CustomerRequest customerRequest) throws Exception {

        Boolean userExist = userService
                .findByUsernameOrEmail(customerRequest.getUsername(), customerRequest.getEmail())
                .isPresent();

        if(userExist){
            throw new RuntimeException(
                    "User with username " +
                    customerRequest.getUsername() +
                    " or email "+customerRequest.getEmail()
            );
        }

        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setSurname(customerRequest.getSurname());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());

        IDNumberData idNumberData = idNumberParser.parse(String.valueOf(customerRequest.getIdNum()));

        if(idNumberData.isValid()){
            customer.setIdNumber(customerRequest.getIdNum());
        }
        else{
            throw new Exception("Please enter a valid ID.");
        }
        customer.setEmail(customerRequest.getEmail());
        customer.setUsername(customerRequest.getUsername());
       // customer.setRoles(customerRequest.getRole());

        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        customer.setPassword(passwordEncoder.encode((generatedString)));
        emailService.sendTextMail("ntombanash",generatedString,
                customerRequest.getUsername(), "nko@gmail",Locale.US);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER_ROLE").get();
        roles.add(userRole);
        customer.setRole(roles);
        //customer.setRoles(roles);
        customer.getRole();
        customerRepository.save(customer);

        return new CustomerResponse(jwtSecurityUtil.generateToken(customerRequest.getEmail()));
    }

    @Override
    public CustomerResponse login(LoginRequest loginRequest) {

       Authentication authentication = authenticationManager
               .authenticate(
               new UsernamePasswordAuthenticationToken(
               loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext()
                .setAuthentication(
                authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginRequest.getUsernameOrEmail(), loginRequest.getPassword())));

        return new CustomerResponse(jwtSecurityUtil.generateToken((userService.getUserEmailBy(loginRequest.getUsernameOrEmail()))));
    }

    @Override
    public void updateCustomerDetails(UUID id, CustomerRequest customerRequest) {

       Customer customer = customerRepository
               .findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Customer Does not exist"));

        customer.setIdNumber(customerRequest.getIdNum());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setName(customerRequest.getName());
        customer.setSurname(customerRequest.getSurname());
        customerRepository.save(customer);

     }

    @Override
    public Customer getCustomerByCustomerId(UUID id) {


        return customerRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Customer Does not exist"));
    }
}
