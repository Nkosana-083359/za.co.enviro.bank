package za.co.envirobank.envirobank.service;

public interface AccessControlService {
    void resetPassword(String username);

    void changePassword(String token, String password);

}
