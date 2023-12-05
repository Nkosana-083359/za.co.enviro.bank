package za.co.envirobank.envirobank.transfereobject;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationResponse {

    private String token;
    private String role;
    private String id;
    private String customerId;
    private String initials;
}
