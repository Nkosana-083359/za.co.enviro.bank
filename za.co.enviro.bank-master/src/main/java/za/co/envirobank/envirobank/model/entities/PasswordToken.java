package za.co.envirobank.envirobank.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String token;
    @Column(name = "token_creation_date")
    private LocalDateTime tokenCreationDate;
    @Column(name = "password_modification_date")
    private LocalDateTime passwordModificationDate;
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(nullable = false,name = "customer_id")
    private UserEntity user;

    public PasswordToken(String token,
                         LocalDateTime tokenCreationDate,
                         LocalDateTime passwordModificationDate,
                         UserEntity user){
        this.token = token;
        this.tokenCreationDate = tokenCreationDate;
        this.passwordModificationDate = passwordModificationDate;
        this.user = user;
    }


}
