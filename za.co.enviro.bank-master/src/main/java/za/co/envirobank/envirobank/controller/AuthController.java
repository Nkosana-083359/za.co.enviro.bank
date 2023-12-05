package za.co.envirobank.envirobank.controller;


import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import za.co.envirobank.envirobank.exceptions.InputException;
import za.co.envirobank.envirobank.model.entities.UserEntity;
import za.co.envirobank.envirobank.respository.UsersRepository;
import za.co.envirobank.envirobank.service.AccessControlService;
import za.co.envirobank.envirobank.service.CustomerService;
import za.co.envirobank.envirobank.service.UserService;
import za.co.envirobank.envirobank.transfereobject.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private CustomerService customerService;
    private AccessControlService accessControlService;
    private UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository userRepo;

    @PostMapping(value = {"/login"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserAuthenticationResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return customerService.login(loginRequest);
    }

    @PostMapping(value = {"/reset-password"},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> resetPassword(@RequestParam(name = "username") String username) {
        accessControlService.resetPassword(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = {"/change-password"},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        accessControlService.changePassword(changePasswordRequest.getToken(), changePasswordRequest.getPassword());
        return ResponseEntity.ok().build();
    }


    @PermitAll
    @PostMapping(value = {"/update-password"},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeUserPassword(@RequestBody @Valid PasswordUpdateRequest changePasswordRequest) {
        UserEntity user = userService.findByUsernameOrEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()).get();

        if (userService.isOldPasswordValid(user, changePasswordRequest.getOldPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepo.save(user);
            System.out.println("New Password: " + user.getPassword());
            return ResponseEntity.ok().body("Password Successfully");
        } else {
            System.out.println("Old Password: " + user.getPassword());
            throw new InputException("Pass not updated. You entered wrong current password.");
        }
    }

}