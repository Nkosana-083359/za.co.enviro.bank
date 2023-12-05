package za.co.envirobank.envirobank.transfereobject;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
@Getter
@Setter
public class TransferRequest {

    private String sourceAccount;

    private BigDecimal amountToWTransfer;

    private String reference;

    private String destinationAccount;

}
