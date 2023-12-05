package za.co.envirobank.envirobank.transfereobject;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class WithdrawRequest {
    @NotEmpty
   private String accNum;


    @NotEmpty
    //add account number
    private BigDecimal amountToWithdraw;


}
