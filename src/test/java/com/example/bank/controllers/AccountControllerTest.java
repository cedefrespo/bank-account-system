package com.example.bank.controllers;

import com.example.bank.models.Account;
import com.example.bank.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount_Success() {
        // Arrange
        Account account = new Account();
        account.setName("John Doe");
        account.setAccountNumber("123456789");

        Account savedAccount = new Account();
        savedAccount.setId("1");
        savedAccount.setName("John Doe");
        savedAccount.setAccountNumber("123456789");
        savedAccount.setBalance(BigDecimal.ZERO);

        when(accountService.createAccount(account)).thenReturn(savedAccount);

        // Act
        ResponseEntity<?> response = accountController.createAccount(account);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedAccount, response.getBody());

        verify(accountService, times(1)).createAccount(account);
    }

    @Test
    void testGetBalance_Success() {
        // Arrange
        Account account = new Account();
        account.setId("1");
        account.setName("John Doe");
        account.setAccountNumber("123456789");
        account.setBalance(BigDecimal.valueOf(500));

        when(accountService.getAccountById("1")).thenReturn(Optional.of(account));

        // Act
        ResponseEntity<?> response = accountController.getBalance("1");

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(BigDecimal.valueOf(500), response.getBody());

        verify(accountService, times(1)).getAccountById("1");
    }

    @Test
    void testGetBalance_NotFound() {
        // Arrange
        when(accountService.getAccountById("1")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = accountController.getBalance("1");

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

        verify(accountService, times(1)).getAccountById("1");
    }
}
