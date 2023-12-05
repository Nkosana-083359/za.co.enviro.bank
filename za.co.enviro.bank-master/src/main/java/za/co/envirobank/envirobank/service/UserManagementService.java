package za.co.envirobank.envirobank.service;

import za.co.envirobank.envirobank.model.entities.PasswordToken;

public interface UserManagementService {

    PasswordToken resetPassword(String username);
    void changePassword(String token, String password);
}
