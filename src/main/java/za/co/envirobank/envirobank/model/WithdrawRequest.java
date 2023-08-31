package za.co.envirobank.envirobank.model;


import java.math.BigDecimal;

public class WithdrawRequest {
   private String accNum;

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    //add account number
    private BigDecimal amountToWithdraw;

    public BigDecimal getAmountToWithdraw() {
        return amountToWithdraw;
    }

    public void setAmountToWithdraw(BigDecimal amountToWithdraw) {
        this.amountToWithdraw = amountToWithdraw;
    }
}
