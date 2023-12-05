package za.co.envirobank.envirobank.service;

import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.model.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    Optional<UserEntity> findByUsernameOrEmail(String emailOrUsername) ;
    String getUserEmailBy(String username);

    Boolean isOldPasswordValid(UserEntity user,String oldPassword);

    List<UserEntity> getAllUser();

}