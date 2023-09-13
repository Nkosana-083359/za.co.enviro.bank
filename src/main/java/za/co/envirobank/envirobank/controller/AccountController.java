package za.co.envirobank.envirobank.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.envirobank.envirobank.exceptions.EntityNotFoundException;
import za.co.envirobank.envirobank.exceptions.InsufficientBalanceException;
import za.co.envirobank.envirobank.exceptions.WithdrawalUnavailableException;
import za.co.envirobank.envirobank.model.entities.Account;
import za.co.envirobank.envirobank.model.entities.Customer;
import za.co.envirobank.envirobank.service.impl.CustomerServiceImpl;
import za.co.envirobank.envirobank.transfereobject.AccountsResponse;
import za.co.envirobank.envirobank.transfereobject.WithdrawRequest;
import za.co.envirobank.envirobank.service.impl.AccountServiceImpl;
import za.co.envirobank.envirobank.utils.AppConstants;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path="/api/transaction/")
public class AccountController {

    private final AccountServiceImpl accountService;
    private final CustomerServiceImpl service;

    @GetMapping("withdraw/")
    //Remove account param
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

    @GetMapping
    public AccountsResponse getAccounts(
            @RequestParam(value = "pageNumber", defaultValue = "0")int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(name = "customerId",required = false)Integer customerId){
        if(customerId == null){
            return accountService.findAll( pageNo,pageSize,sortBy,sortDir );
        }else{Customer customer = service.getCustomerByCustomerId(customerId);
            List<Account> account = accountService.getAccountByCustomerId(customer);
            return (AccountsResponse) account;
        }

    }

//    @GetMapping("/account")
//    public List<Account> getAccount(@RequestParam Integer customerId){
//
//       Customer customer = service.getCustomerByCustomerId(customerId);
//        List<Account> account = accountService.getAccountByCustomerId(customer);
//        return account;
//
//    }

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

