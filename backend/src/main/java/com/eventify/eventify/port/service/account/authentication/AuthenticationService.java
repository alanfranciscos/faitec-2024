package com.eventify.eventify.port.service.account.authentication;

/**
 * Interface for authentication services.
 * Provides methods for authenticating users based on their email and password.
 */
public interface AuthenticationService {

    /**
     * Authenticates a user based on the provided email and password.
     *
     * @param email the email address of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return a string containing authentication token
     */
    String Login(String email, String password);
}
