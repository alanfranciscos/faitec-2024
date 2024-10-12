package com.eventify.eventify.port.service.account;

import com.eventify.eventify.models.account.Account;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for managing user accounts.
 */
public interface AccountService {


    /**
     * Registers a new user with the provided username, email, password, and profile image data.
     *
     * @param username the username of the new user
     * @param email    the email address of the new user
     * @param password the password for the new user
     * @return an Integer representing the ID of the newly registered user
     */
    Integer RegisterUser(String username, String email, String password);

    /**
     * Resets the password for the user associated with the given email.
     *
     * @param email    the email address of the user
     * @param password the new password for the user
     * @return user email if the password is successfully reset, null otherwise
     */
    String forgotPassword(String email, String password);

    /**
     * Verifies the user's account using the provided email and verification code.
     *
     * @param email the email address of the user
     * @param code  the verification code sent to the user's email
     * @return true if the account is successfully verified, false otherwise
     */
    boolean verifyAccount(String email, String code);


    /**
     * Retrieves the account request.
     *
     * @return the account request.
     */
    Account getAccountRequest();


    /**
     * Retrieves the account associated with the given ID.
     *
     * @param accountId the ID of the account to retrieve
     * @return the account associated with the given ID
     */
    Account getAccountById(int accountId);

    /**
     * Retrieves the account associated with the given email.
     * @param email the email of the account to retrieve
     * @return the account associated with the given email
     */
    Account getAccountByEmail(String email);


    /**
     * Updates the profile image for the user associated with the given ID.
     *
     * @param id        the ID of the user
     * @param imageData the new profile image data for the user
     */
    void updateImage(int id, MultipartFile imageData);


    /**
     * Deletes the account associated with the given ID.
     *
     * @param id the ID of the account to delete
     */
    void deleteAccount(int id);
}
