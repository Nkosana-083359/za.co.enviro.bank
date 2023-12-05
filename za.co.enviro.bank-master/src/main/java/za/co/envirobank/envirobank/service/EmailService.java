package za.co.envirobank.envirobank.service;

import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.model.entities.PasswordToken;

@Service
public interface EmailService {


  void sendSignupEmail(String name,String password);
  void sendResetEmailPassword(PasswordToken tokenService);

}
