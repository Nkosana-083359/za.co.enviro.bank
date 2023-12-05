package za.co.envirobank.envirobank.service;

import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.model.entities.PasswordToken;
@Service
public interface PasswordTokenService {
    void savePasswordToken(PasswordToken token);
    PasswordTokenService getToken(String token);

    int passwordModificationDate(String token);
}
