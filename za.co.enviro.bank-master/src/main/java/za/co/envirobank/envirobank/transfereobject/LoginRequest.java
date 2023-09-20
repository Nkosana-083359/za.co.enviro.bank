package za.co.envirobank.envirobank.transfereobject;

import lombok.Data;

@Data
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
