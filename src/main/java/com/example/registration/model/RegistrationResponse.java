package com.example.registration.model;

/**
 * Response object used for registration and withdrawal actions.
 *
 * <p>This allows the backend to send both a success/failure result
 * and a clear user-facing message back to the frontend.</p>
 */
public class RegistrationResponse {

    private boolean success;
    private String message;

    public RegistrationResponse() {
    }

    public RegistrationResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}