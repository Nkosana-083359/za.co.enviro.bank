package za.co.envirobank.envirobank.transfereobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    private String customerName;
    private String customerSurname;
    private String customerEmail;
    private String customerPhoneNumber;
    private String customerIdNumber;
    private String customerUsername;
}
