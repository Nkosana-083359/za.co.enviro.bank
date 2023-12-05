package za.co.envirobank.envirobank.transfereobject;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import za.co.envirobank.envirobank.model.entities.Role;

import java.util.Set;


@Data
public class CustomerRequest {
    @NotEmpty
    @Size(min = 2, message = "Customer name should have at least 2 characters")
    private String name;
    @NotEmpty
    @Size(min = 2, message = "Customer surname should have at least 2 characters")
    private String surname;

    @NotNull(message = "ID number is required")
    @Pattern(regexp = "\\d{13}",message = "Invalid identity number.")
    private String idNum;
    private String phoneNumber;
    @Email
    private String email;
    private String username;


}
