package za.co.envirobank.envirobank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.model.entities.UserEntity;
import za.co.envirobank.envirobank.respository.UsersRepository;
import za.co.envirobank.envirobank.service.UserService;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Override
    public Optional<UserEntity> findByUsernameOrEmail(String email, String username) {
        return usersRepository.findByUsernameOrEmail(email,username);
    }

    @Override
    public String getUserEmailBy(String username) {

        return usersRepository.findByUsernameOrEmail(username,username).get().getEmail();
    }


}