package za.co.envirobank.envirobank.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.envirobank.envirobank.exceptions.EntityNotFoundException;
import za.co.envirobank.envirobank.exceptions.InsufficientBalanceException;
import za.co.envirobank.envirobank.exceptions.WithdrawalUnavailableException;
import za.co.envirobank.envirobank.model.WithdrawRequest;
import za.co.envirobank.envirobank.service.impl.WithdrawalService;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path="api/v1/transaction")
public class WithdrawController {

    private final WithdrawalService accountService;

    @PostMapping("/withdraw/")
    //Remove account param
    public ResponseEntity<String> withdraw(@RequestBody WithdrawRequest request) {
        BigDecimal amountToWithdraw = request.getAmountToWithdraw();
        String accountNum = request.getAccNum();

        try {
            accountService.withdraw(accountNum, amountToWithdraw);
            return ResponseEntity.ok("Withdrawal successful");
        } catch (WithdrawalUnavailableException e) {
            return ResponseEntity.badRequest().body("Withdrawal failed: " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.badRequest().body("Withdrawal failed: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> softDeletion(@PathVariable UUID accountId){
        try{
            accountService.softDelete(accountId);
            return ResponseEntity.ok("Record deleted.");
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    }
    }

