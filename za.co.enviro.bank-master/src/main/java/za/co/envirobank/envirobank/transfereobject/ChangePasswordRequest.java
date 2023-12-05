package za.co.envirobank.envirobank.transfereobject;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    @NotEmpty
    private String token;
    @NotEmpty
    private String password;


}
