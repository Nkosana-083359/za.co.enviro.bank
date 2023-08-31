package za.co.envirobank.envirobank.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.envirobank.envirobank.model.WithdrawRequest;
import za.co.envirobank.envirobank.service.impl.SavingsAccount;

import java.math.BigDecimal;
@AllArgsConstructor
@RestController
@RequestMapping(path="api/v1/transaction")
public class WithdrawController {

    private final SavingsAccount accountService;

    @PostMapping("/withdraw/")
    //Remove account param
    public ResponseEntity<String> withdraw( @RequestBody WithdrawRequest request) {
        BigDecimal amountToWithdrawStr = request.getAmountToWithdraw();
        String accountNum = request.getAccNum();
        try {
            accountService.withdraw(accountNum,  amountToWithdrawStr);
            return ResponseEntity.ok("Withdrawal successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Withdrawal failed: " + e.getMessage());
        }
    }
}
