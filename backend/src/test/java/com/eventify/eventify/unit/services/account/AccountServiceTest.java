package com.eventify.eventify.unit.services.account;

import static org.mockito.Mockito.*;

import com.eventify.eventify.dto.account.RegisterRequestDTO;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.repository.account.AccountRepository;
import com.eventify.eventify.repository.account.password.AccountPasswordHistoryRepository;
import com.eventify.eventify.services.account.AccountService;
import com.eventify.eventify.services.email.EmailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountPasswordHistoryRepository accountPasswordHistoryRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AccountService accountService;

    private AccountPasswordHistory createAccountPasswordHistory(Account account, String password)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        AccountPasswordHistory accountPasswordHistory = new AccountPasswordHistory(account);
        accountPasswordHistory.setVerificationCode("123456", 5);
        accountPasswordHistory.setStaging(true);
        accountPasswordHistory.setPassword(password);

        AccountPasswordHistory passwordStagingAreaSaved = Mockito.spy(accountPasswordHistory);

        Field passwordField = AccountPasswordHistory.class.getDeclaredField("id");
        passwordField.setAccessible(true);
        passwordField.set(passwordStagingAreaSaved, 1L);

        when(accountPasswordHistoryRepository.save(any(AccountPasswordHistory.class)))
                .thenReturn(passwordStagingAreaSaved);

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

        when(accountRepository.findByEmail(registerRequestDTO.email())).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(accountSaved);

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

        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(
                account.getUsername(),
                account.getEmail(),
                passwordAccount, null);

        // Act
        Account result = accountService.RegisterUser(registerRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(registerRequestDTO.email(), result.getEmail());
        assertEquals(registerRequestDTO.username(), result.getUsername());
        assertEquals(registerRequestDTO.imageData(), result.getImageData());
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
        when(accountRepository.findByEmail(account.getEmail())).thenReturn(Optional.of(account));

        when(accountPasswordHistoryRepository.findByAccountIdAndStaging(account.getId(), true))
                .thenReturn(Optional.empty());
        when(accountPasswordHistoryRepository.findByAccountId(account.getId()))
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

        when(accountRepository.findByEmail(account.getEmail())).thenReturn(Optional.of(account));
        when(accountPasswordHistoryRepository.findByAccountIdAndStaging(account.getId(), true))
                .thenReturn(Optional.of(accountPasswordHistory));

        // Act
        boolean result = accountService.verifyAccount(account.getEmail(), "123456");

        // Assert
        assertTrue(result);
    }

}