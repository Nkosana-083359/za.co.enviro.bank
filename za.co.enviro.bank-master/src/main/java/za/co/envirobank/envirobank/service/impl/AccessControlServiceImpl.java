package za.co.envirobank.envirobank.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.model.entities.PasswordToken;
import za.co.envirobank.envirobank.respository.UsersRepository;
import za.co.envirobank.envirobank.service.AccessControlService;
import za.co.envirobank.envirobank.service.EmailService;
import za.co.envirobank.envirobank.service.UserManagementService;

import java.util.*;


@Service
@Slf4j

@AllArgsConstructor

public class AccessControlServiceImpl implements AccessControlService {

    private final UserManagementService userManagementService;
    private EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private UsersRepository usersRepository;

    @Override
    public void resetPassword(String username) {
        PasswordToken passwordResetEntity = userManagementService.resetPassword(username);
        emailService.sendResetEmailPassword(passwordResetEntity);

    }

    @Override
    public void changePassword(String token, String password) {
        if (Objects.nonNull(token)) {
            String encodedPassword = passwordEncoder.encode(password);
            userManagementService.changePassword(token, encodedPassword);
        }
    }

}
