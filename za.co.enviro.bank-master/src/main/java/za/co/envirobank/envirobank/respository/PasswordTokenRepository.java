package za.co.envirobank.envirobank.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.envirobank.envirobank.model.entities.PasswordToken;

import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken,Long> {

    Optional<PasswordToken> findByToken(String token);

}
