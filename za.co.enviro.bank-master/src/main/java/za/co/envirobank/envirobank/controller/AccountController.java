package za.co.envirobank.envirobank.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.co.envirobank.envirobank.exceptions.AmountException;
import za.co.envirobank.envirobank.exceptions.EntityNotFoundException;
import za.co.envirobank.envirobank.exceptions.InsufficientBalanceException;
import za.co.envirobank.envirobank.exceptions.WithdrawalUnavailableException;
import za.co.envirobank.envirobank.service.impl.CustomerServiceImpl;
import za.co.envirobank.envirobank.transfereobject.TransferRequest;
import za.co.envirobank.envirobank.transfereobject.WithdrawRequest;
import za.co.envirobank.envirobank.service.impl.AccountServiceImpl;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path="/api/transaction/")
public class AccountController {

    private final AccountServiceImpl accountService;
    private final CustomerServiceImpl service;

    @PreAuthorize(value = "hasRole({'USER'})")
    @GetMapping("withdraw/")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawRequest request) {
        BigDecimal amountToWithdraw = request.getAmountToWithdraw();
        String accountNum = request.getAccNum();
        if(amountToWithdraw.compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("Invalid amount");
        }

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

    @PermitAll
    @PostMapping("accounts/transfer")
    public ResponseEntity<String> transfer( @RequestBody  TransferRequest transferRequest){
        BigDecimal amountToWTransfer = transferRequest.getAmountToWTransfer();
        String accountNum = transferRequest.getSourceAccount();
        if(amountToWTransfer.compareTo(BigDecimal.ZERO)<=0){
            throw new AmountException("Invalid amount");
        }

        accountService.transfer(transferRequest.getSourceAccount(),
                transferRequest.getAmountToWTransfer(),
                transferRequest.getDestinationAccount(),
                transferRequest.getReference() );
        return ResponseEntity.ok("transfer successful");

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

