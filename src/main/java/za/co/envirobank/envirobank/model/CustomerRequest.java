package za.co.envirobank.envirobank.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
    public String name;
    public String surname;

    public Integer idNum;
    public String phoneNumber;
}
