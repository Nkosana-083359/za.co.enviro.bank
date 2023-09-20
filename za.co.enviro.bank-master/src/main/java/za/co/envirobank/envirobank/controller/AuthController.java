package za.co.envirobank.envirobank.controller;



import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.envirobank.envirobank.service.CustomerService;
import za.co.envirobank.envirobank.service.EmailService;
import za.co.envirobank.envirobank.transfereobject.CustomerResponse;
import za.co.envirobank.envirobank.transfereobject.LoginRequest;

import java.util.Locale;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private CustomerService customerService;


    // Build Login REST API
    @PostMapping(value = {"/login"})
    public ResponseEntity<CustomerResponse> login(@RequestBody LoginRequest loginRequest) throws MessagingException {

        return ResponseEntity.ok(customerService.login(loginRequest));
    }

    // Build Register REST API
//    @PostMapping(value = {"/register", "/signup"})
//    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
//        String response = authService.register(registerDto);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
}