package com.example.bank.services;

import com.example.bank.events.DepositEvent;
import com.example.bank.events.DepositEventHandler;
import com.example.bank.models.Account;
import com.example.bank.models.Transaction;
import com.example.bank.repositories.AccountRepository;
import com.example.bank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DepositEventHandler depositEventHandler;  // Inyectamos el manejador de eventos

    public Transaction createTransaction(Transaction transaction) throws Exception {
        BigDecimal amount = transaction.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Transaction amount must be greater than 0");
        }

        Account account = accountRepository.findById(transaction.getAccountId())
                .orElseThrow(() -> new Exception("Account not found"));

        switch (transaction.getType().toLowerCase()) {
            case "deposit":
                account.setBalance(account.getBalance().add(amount));

                // Emitir evento de depósito
                DepositEvent depositEvent = new DepositEvent(account.getId(), amount, "deposit");
                depositEventHandler.handleDepositEvent(depositEvent);
                break;

            case "withdrawal":
                if (account.getBalance().compareTo(amount) < 0) {
                    throw new Exception("Insufficient funds");
                }
                account.setBalance(account.getBalance().subtract(amount));
                break;

            default:
                throw new Exception("Invalid transaction type. Use 'deposit' or 'withdrawal'");
        }

        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }
}
