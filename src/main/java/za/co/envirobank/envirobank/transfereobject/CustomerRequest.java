package za.co.envirobank.envirobank.transfereobject;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class CustomerRequest {
    @NotEmpty
    @Size(min = 2, message = "Customer name should have at least 2 characters")
    private String name;
    @NotEmpty
    @Size(min = 2, message = "Customer name should have at least 10 characters")
    private String surname;

    @NotNull(message = "ID number is required")
   // @Pattern(regexp = "\\d{13}",message = "Invalid identity number.")
    private long idNum;

    private String phoneNumber;

    private String email;

    private String password;


}
