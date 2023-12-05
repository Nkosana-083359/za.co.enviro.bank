package za.co.envirobank.envirobank.transfereobject;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty
    private String usernameOrEmail;
    @NotEmpty
    private String password;
}
