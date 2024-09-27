package com.eventify.eventify.unit.services.account;

import com.eventify.eventify.dto.account.RegisterRequestDTO;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.port.dao.account.AccountDao;
import com.eventify.eventify.port.dao.account.password.AccountPasswordHistoryDao;
import com.eventify.eventify.services.account.AccountServiceImpl;
import com.eventify.eventify.services.email.EmailServiceImpl;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class AccountServiceTest {

    @Mock
    private AccountDao accountDao;

    @Mock
    private AccountPasswordHistoryDao accountPasswordHistoryDao;

    @Mock
    private EmailServiceImpl emailService;

    @InjectMocks
    private AccountServiceImpl accountService;

    private AccountPasswordHistory createAccountPasswordHistory(Account account, String password)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        AccountPasswordHistory accountPasswordHistory = new AccountPasswordHistory(account.getId());
        accountPasswordHistory.setVerificationCode("123456", 5);
        accountPasswordHistory.setStaging(true);
        accountPasswordHistory.setPassword(password);

        AccountPasswordHistory passwordStagingAreaSaved = Mockito.spy(accountPasswordHistory);

        Field passwordField = AccountPasswordHistory.class.getDeclaredField("id");
        passwordField.setAccessible(true);
        passwordField.set(passwordStagingAreaSaved, 1L);

        when(accountPasswordHistoryDao.save(any(AccountPasswordHistory.class)))
                .thenReturn(1);

        return passwordStagingAreaSaved;
    }

    private Account createAccount()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(
                "usernameteste",
                "emailTeste@teste.com",
                "passwordTeste",
                null
        );
        Account account = new Account();
        account.setEmail(registerRequestDTO.email());
        account.setUsername(registerRequestDTO.username());
        account.setImageData(registerRequestDTO.imageData());

        Account accountSaved = Mockito.spy(account);

        Field accountField = Account.class.getDeclaredField("id");
        accountField.setAccessible(true);
        accountField.set(accountSaved, 1L);

        when(accountDao.readByEmail(registerRequestDTO.email())).thenReturn(null);
        when(accountDao.save(any(Account.class))).thenReturn(1);

        return accountSaved;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        String passwordAccount = "password@teste.com";
        Account account = new Account();
        try {
            account = createAccount();
            createAccountPasswordHistory(account, passwordAccount);
        } catch (Exception e) {
            fail("Error creating user");
        }

        // Act
        int accountId = accountService.RegisterUser(
                account.getUsername(),
                account.getEmail(),
                passwordAccount, null
        );

        // Assert
        assertNotNull(accountId);
    }

    @Test
    public void testForgotPassword() {
        // Arrange
        String passwordAccount = "password@teste.com";
        Account account = new Account();
        AccountPasswordHistory accountPasswordHistory = null;
        try {
            account = createAccount();
            accountPasswordHistory = createAccountPasswordHistory(account, passwordAccount);
        } catch (Exception e) {
            fail("Error creating user");
        }
        when(accountDao.readByEmail(account.getEmail())).thenReturn(account);

        when(accountPasswordHistoryDao.findByAccountIdAndStaging(account.getId(), true))
                .thenReturn(Optional.empty());
        when(accountPasswordHistoryDao.findByAccountId(account.getId()))
                .thenReturn(List.of());

        // Act
        String result = accountService.forgotPassword(account.getEmail(), "123456");

        // Assert
        assertEquals(account.getEmail(), result);
    }

    @Test
    public void testVerifyAccount() {
        // Arrange
        String passwordAccount = "password@teste.com";
        Account account = new Account();
        AccountPasswordHistory accountPasswordHistory = null;
        try {
            account = createAccount();
            accountPasswordHistory = createAccountPasswordHistory(account, passwordAccount);
        } catch (Exception e) {
            fail("Error creating user");
        }

        when(accountDao.readByEmail(account.getEmail())).thenReturn(account);
        when(accountPasswordHistoryDao.findByAccountIdAndStaging(account.getId(), true))
                .thenReturn(Optional.of(accountPasswordHistory));

        // Act
        boolean result = accountService.verifyAccount(account.getEmail(), "123456");

        // Assert
        assertTrue(result);
    }

}
