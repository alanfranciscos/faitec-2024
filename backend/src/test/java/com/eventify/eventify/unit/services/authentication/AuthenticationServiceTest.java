package com.eventify.eventify.unit.services.authentication;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.port.dao.account.AccountDao;
import com.eventify.eventify.port.dao.account.password.AccountPasswordHistoryDao;
import com.eventify.eventify.services.account.authentication.AuthenticationServiceImpl;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

class AuthenticationServiceTest {

       @Mock
    private AccountDao accountDao;

    @Mock
    private AccountPasswordHistoryDao accountPasswordHistoryDao;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

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

        AccountPasswordHistory accountPasswordHistory = new AccountPasswordHistory(account.getId());
        accountPasswordHistory.setPassword(password);

        when(accountDao.readByEmail(email)).thenReturn(account);
        when(accountPasswordHistoryDao.findByAccountIdAndActive(account.getId(), true))
            .thenReturn(Optional.of(accountPasswordHistory));

        // Act
        String token = authenticationService.Login(email, password);

        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void verifyUserPassword_WhenEmailIsNull_ShouldThrowRuntimeException() {
        // Arrange
        String password = "password";

        // Act & Assert
        assertThrows(
            RuntimeException.class, () -> authenticationService
            .Login(null, password)
        );
    }

    @Test
    void verifyUserPassword_WhenPasswordIsNull_ShouldThrowRuntimeException() {
        // Arrange
        String email = "test@example.com";

        // Act & Assert
        assertThrows(
            RuntimeException.class, () -> authenticationService
            .Login(email, null)
        );
    }

    @Test
    void verifyUserPassword_WhenEmailDoesNotExist_ShouldThrowRuntimeException() {
        // Arrange
        String email = "test@example.com";
        String password = "password";

        when(accountDao.readByEmail(email)).thenReturn(null);

        // Act & Assert
        assertThrows(
            RuntimeException.class, () -> authenticationService
            .Login(email, password)
        );
    }

    @Test
    void verifyUserPassword_WhenPasswordIsIncorrect_ShouldThrowRuntimeException() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        Account account = new Account();
        account.setEmail(email);

        AccountPasswordHistory accountPasswordHistory = new AccountPasswordHistory(account.getId());
        accountPasswordHistory.setPassword("incorrect_password");

        when(accountPasswordHistoryDao.findByAccountIdAndActive(account.getId(), true))
            .thenReturn(Optional.of(accountPasswordHistory));

        when(accountDao.readByEmail(email)).thenReturn(account);

        // Act & Assert
        assertThrows(
            RuntimeException.class, () -> authenticationService
            .Login(email, password)
        );
    }
}