package za.co.envirobank.envirobank.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.exceptions.ResourceExpired;
import za.co.envirobank.envirobank.model.entities.PasswordToken;
import za.co.envirobank.envirobank.model.entities.UserEntity;
import za.co.envirobank.envirobank.respository.PasswordTokenRepository;
import za.co.envirobank.envirobank.respository.UsersRepository;
import za.co.envirobank.envirobank.service.UserManagementService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {
    private UsersRepository usersRepository;
    private PasswordTokenRepository passwordTokenRepository;

    @Override
    public PasswordToken resetPassword(String username) {
        UserEntity user = usersRepository.findByUsernameOrEmail(username, username).orElseThrow(
                () -> new EntityNotFoundException("Could Not find user with with username" + username)
        );
        PasswordToken passwordReset = new PasswordToken();
        passwordReset.setUser(user);
        passwordReset.setToken(UUID.randomUUID().toString());
        passwordReset.setExpiryDate(LocalDateTime.now().plusMinutes(1));
        passwordReset.setTokenCreationDate(LocalDateTime.now());
        return passwordTokenRepository.save(passwordReset);
    }

    @Override
    public void changePassword(String token, String password) {
        Optional<PasswordToken> passwordResetEntity = passwordTokenRepository
                .findByToken(token);

        passwordResetEntity.ifPresent(pr -> {
            boolean isTokenValid = pr.getExpiryDate().isBefore(LocalDateTime.now());
            if (!isTokenValid) {
                UserEntity user = pr.getUser();
                user.setPassword(password);
                usersRepository.save(user);
                passwordTokenRepository.save(pr);
            } else {
                throw new ResourceExpired("token expired");
            }

        });
    }

    //

}

