package com.example.bank.services;

import com.example.bank.models.Account;
import com.example.bank.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        // Arrange
        Account account = new Account();
        account.setName("John Doe");
        account.setAccountNumber("123456789");

        Account savedAccount = new Account();
        savedAccount.setId("1");
        savedAccount.setName("John Doe");
        savedAccount.setAccountNumber("123456789");
        savedAccount.setBalance(BigDecimal.ZERO);

        when(accountRepository.save(account)).thenReturn(savedAccount);

        // Act
        Account result = accountService.createAccount(account);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("123456789", result.getAccountNumber());
        assertEquals(BigDecimal.ZERO, result.getBalance());

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testGetAccountById_Found() {
        // Arrange
        Account account = new Account();
        account.setId("1");
        account.setName("John Doe");
        account.setAccountNumber("123456789");
        account.setBalance(BigDecimal.ZERO);

        when(accountRepository.findById("1")).thenReturn(Optional.of(account));

        // Act
        Optional<Account> result = accountService.getAccountById("1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
        assertEquals("John Doe", result.get().getName());

        verify(accountRepository, times(1)).findById("1");
    }

    @Test
    void testGetAccountById_NotFound() {
        // Arrange
        when(accountRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        Optional<Account> result = accountService.getAccountById("1");

        // Assert
        assertFalse(result.isPresent());

        verify(accountRepository, times(1)).findById("1");
    }
}
