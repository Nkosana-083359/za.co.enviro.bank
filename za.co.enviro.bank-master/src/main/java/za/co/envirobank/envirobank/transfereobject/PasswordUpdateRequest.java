package za.co.envirobank.envirobank.transfereobject;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {

    @NotEmpty
    private String newPassword;
    @NotEmpty
    private String oldPassword;

}
