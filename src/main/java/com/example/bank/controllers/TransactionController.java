package com.example.bank.controllers;

import com.example.bank.models.Transaction;
import com.example.bank.services.TransactionService;
import com.example.bank.events.DepositEvent;
import com.example.bank.events.DepositEventHandler;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DepositEventHandler depositEventHandler;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        try {
            // Verificar si el depósito es grande
            if (transaction.getAmount().compareTo(BigDecimal.valueOf(10000)) > 0) {
                DepositEvent depositEvent = new DepositEvent(
                    transaction.getAccountId(),
                    transaction.getAmount(),
                    "deposit"
                );

                // Manejar el evento de depósito
                depositEventHandler.handleDepositEvent(depositEvent);
            }
            
            return ResponseEntity.ok(transactionService.createTransaction(transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
