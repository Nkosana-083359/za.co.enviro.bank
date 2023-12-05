package za.co.envirobank.envirobank.service.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.exceptions.EntityNotFoundException;
import za.co.envirobank.envirobank.exceptions.InputException;
import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.model.entities.Role;
import za.co.envirobank.envirobank.model.entities.UserEntity;
import za.co.envirobank.envirobank.respository.CustomerRepository;
import za.co.envirobank.envirobank.respository.RoleRepository;
import za.co.envirobank.envirobank.service.CustomerService;
import za.co.envirobank.envirobank.service.EmailService;
import za.co.envirobank.envirobank.service.UserService;
import za.co.envirobank.envirobank.service.za.id.validator.IDNumberParser;
import za.co.envirobank.envirobank.service.za.id.validator.IDNumberData;
import za.co.envirobank.envirobank.transfereobject.*;
import za.co.envirobank.envirobank.utils.JwtSecurityUtil;

import java.util.*;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final JwtSecurityUtil jwtSecurityUtil;
    private EmailService emailService;
    private final IDNumberParser idNumberParser;


    @Override
    public UserAuthenticationResponse registerCustomer(CustomerRequest customerRequest) {

        Boolean userExist = userService
                .findByUsernameOrEmail(customerRequest.getUsername())
                .isPresent();

        if (userExist) {
            throw new InputException("Username or email already exists.");
            //throw ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }

        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setSurname(customerRequest.getSurname());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());

        IDNumberData idNumberData = idNumberParser.parse(String.valueOf(customerRequest.getIdNum()));

        if (idNumberData.isValid()) {
            customer.setIdNumber(customerRequest.getIdNum());
        } else {
            throw new InputException("Please enter a valid ID.");
        }
        customer.setEmail(customerRequest.getEmail());
        customer.setUsername(customerRequest.getUsername());


        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        customer.setPassword(passwordEncoder.encode((generatedString)));

        emailService.sendSignupEmail(
                customerRequest.getEmail(), generatedString);

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER").get();
        roles.add(userRole);
        customer.setRole(roles);
        customer.getRole();
        customerRepository.save(customer);
        String initials = String.valueOf(customerRequest.getName().charAt(0) + customerRequest.getSurname().charAt(0));
        return new UserAuthenticationResponse(jwtSecurityUtil.generateToken(customerRequest.getEmail()), userRole.getName(), customer.getId().toString(), customer.getIdNumber(),initials);
    }

    @Override
    public UserAuthenticationResponse login(LoginRequest loginRequest) {
        UserEntity user = userService.findByUsernameOrEmail(
                        loginRequest.getUsernameOrEmail())
                .orElseThrow(() -> new EntityNotFoundException("Invalid credentials"));
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return createAuthenticationResponse(user);
        }
        throw new EntityNotFoundException("Invalid credentials");
    }

    private UserAuthenticationResponse createAuthenticationResponse(UserEntity user) {
        String userEmail = userService.getUserEmailBy(user.getEmail());
        String role = user.getRole().iterator().next().getName();
        String userId = user.getId().toString();
        String idNumber = user.getIdNumber();
        String initials = user.getSurname().substring(0,1) + user.getName().substring(0,1);

        String token = jwtSecurityUtil.generateToken(userEmail);
        return new UserAuthenticationResponse(token, role, userId, idNumber,initials);
    }

    @Override
    public void updateCustomerDetails(String id, CustomerRequest customerRequest) {

        Customer customer = customerRepository
                .findByIdNumber(id);


        customer.setIdNumber(customerRequest.getIdNum());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setName(customerRequest.getName());
        customer.setSurname(customerRequest.getSurname());
        customerRepository.save(customer);

    }

    @Override
    public Customer getAllCustomerById(UUID id) {


        return customerRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Customer Does not exist"));
    }

    @Override
    public Customer getByIdNum(String idNum) {
        return customerRepository.findByIdNumber(idNum);
    }


}
