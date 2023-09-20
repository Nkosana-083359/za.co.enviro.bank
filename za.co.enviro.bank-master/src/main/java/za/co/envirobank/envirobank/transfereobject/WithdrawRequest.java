package za.co.envirobank.envirobank.transfereobject;


import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public class WithdrawRequest {
    @NotEmpty
   private String accNum;

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }
    @NotEmpty
    //add account number
    private BigDecimal amountToWithdraw;

    public BigDecimal getAmountToWithdraw() {
        return amountToWithdraw;
    }

    public void setAmountToWithdraw(BigDecimal amountToWithdraw) {
        this.amountToWithdraw = amountToWithdraw;
    }
}
