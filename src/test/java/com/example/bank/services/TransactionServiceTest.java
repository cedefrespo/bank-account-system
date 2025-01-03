package com.example.bank.services;

import com.example.bank.models.Account;
import com.example.bank.models.Transaction;
import com.example.bank.repositories.AccountRepository;
import com.example.bank.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransaction_Withdrawal_Success() throws Exception {
        // Arrange
        Account account = new Account();
        account.setId("1");
        account.setBalance(BigDecimal.valueOf(1000));

        Transaction transaction = new Transaction();
        transaction.setAccountId("1");
        transaction.setType("withdrawal");
        transaction.setAmount(BigDecimal.valueOf(500));

        when(accountRepository.findById("1")).thenReturn(Optional.of(account));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Act
        Transaction result = transactionService.createTransaction(transaction);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getAccountId());
        assertEquals("withdrawal", result.getType());
        assertEquals(BigDecimal.valueOf(500), result.getAmount());
        assertEquals(BigDecimal.valueOf(500), account.getBalance());

        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testCreateTransaction_Withdrawal_InsufficientFunds() {
        // Arrange
        Account account = new Account();
        account.setId("1");
        account.setBalance(BigDecimal.valueOf(300));

        Transaction transaction = new Transaction();
        transaction.setAccountId("1");
        transaction.setType("withdrawal");
        transaction.setAmount(BigDecimal.valueOf(500));

        when(accountRepository.findById("1")).thenReturn(Optional.of(account));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> 
            transactionService.createTransaction(transaction)
        );

        assertEquals("Insufficient funds", exception.getMessage());
        verify(accountRepository, never()).save(account);
        verify(transactionRepository, never()).save(transaction);
    }
}
