package za.co.envirobank.envirobank.service;

import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.model.entities.UserEntity;

import java.util.Optional;

@Service
public interface UserService {
    Optional<UserEntity> findByUsernameOrEmail(String email,String username);
    String getUserEmailBy(String username);


}