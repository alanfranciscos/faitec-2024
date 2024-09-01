package com.eventify.eventify.unit.services.authentication;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.repository.account.AccountRepository;
import com.eventify.eventify.services.account.authentication.AuthenticationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.repository.account.password.AccountPasswordHistoryRepository;

class AuthenticationServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountPasswordHistoryRepository accountPasswordHistoryRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void verifyUserPassword_WhenEmailAndPasswordAreValid_ShouldReturnAccount() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        Account account = new Account();
        account.setEmail(email);

        AccountPasswordHistory accountPasswordHistory = new AccountPasswordHistory(account);
        accountPasswordHistory.setPassword(password);

        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(account));
        when(accountPasswordHistoryRepository.findByAccountIdAndActive(account.getId(), true))
            .thenReturn(Optional.of(accountPasswordHistory));

        // Act
        Account result = authenticationService.verifyUserPassword(email, password);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void verifyUserPassword_WhenEmailIsNull_ShouldThrowRuntimeException() {
        // Arrange
        String password = "password";

        // Act & Assert
        assertThrows(
            RuntimeException.class, () -> authenticationService
            .verifyUserPassword(null, password)
        );
    }

    @Test
    void verifyUserPassword_WhenPasswordIsNull_ShouldThrowRuntimeException() {
        // Arrange
        String email = "test@example.com";

        // Act & Assert
        assertThrows(
            RuntimeException.class, () -> authenticationService
            .verifyUserPassword(email, null)
        );
    }

    @Test
    void verifyUserPassword_WhenEmailDoesNotExist_ShouldThrowRuntimeException() {
        // Arrange
        String email = "test@example.com";
        String password = "password";

        when(accountRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
            RuntimeException.class, () -> authenticationService
            .verifyUserPassword(email, password)
        );
    }

    @Test
    void verifyUserPassword_WhenPasswordIsIncorrect_ShouldThrowRuntimeException() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        Account account = new Account();
        account.setEmail(email);

        AccountPasswordHistory accountPasswordHistory = new AccountPasswordHistory(account);
        accountPasswordHistory.setPassword("incorrect_password");

        when(accountPasswordHistoryRepository.findByAccountIdAndActive(account.getId(), true))
            .thenReturn(Optional.of(accountPasswordHistory));

        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(account));

        // Act & Assert
        assertThrows(
            RuntimeException.class, () -> authenticationService
            .verifyUserPassword(email, password)
        );
    }
}