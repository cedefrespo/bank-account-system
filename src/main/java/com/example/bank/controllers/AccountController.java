package com.example.bank.controllers;

import com.example.bank.models.Account;
import com.example.bank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<?> getBalance(@PathVariable String id) {
        try {
            Account account = accountService.getAccountById(id)
                .orElseThrow(() -> new Exception("Account not found")); // Lanzar una excepción si no existe
            return ResponseEntity.ok(account.getBalance());
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Manejar la excepción y devolver el mensaje
        }
    }
}
