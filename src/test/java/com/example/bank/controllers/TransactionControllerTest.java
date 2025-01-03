package com.example.bank.controllers;

import com.example.bank.models.Transaction;
import com.example.bank.services.TransactionService;
import com.example.bank.events.DepositEvent;
import com.example.bank.events.DepositEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private DepositEventHandler depositEventHandler;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransaction_Deposit_SmallAmount() throws Exception {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setAccountId("1");
        transaction.setType("deposit");
        transaction.setAmount(BigDecimal.valueOf(5000));

        when(transactionService.createTransaction(transaction)).thenReturn(transaction);

        // Act
        ResponseEntity<?> response = transactionController.createTransaction(transaction);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transaction, response.getBody());

        verify(transactionService, times(1)).createTransaction(transaction);
        verify(depositEventHandler, never()).handleDepositEvent(any(DepositEvent.class));
    }

    @Test
    void testCreateTransaction_Deposit_LargeAmount() throws Exception {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setAccountId("1");
        transaction.setType("deposit");
        transaction.setAmount(BigDecimal.valueOf(15000));

        DepositEvent depositEvent = new DepositEvent("1", BigDecimal.valueOf(15000), "deposit");

        when(transactionService.createTransaction(transaction)).thenReturn(transaction);

        // Act
        ResponseEntity<?> response = transactionController.createTransaction(transaction);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transaction, response.getBody());

        verify(transactionService, times(1)).createTransaction(transaction);
        verify(depositEventHandler, times(1)).handleDepositEvent(refEq(depositEvent));
    }
}
