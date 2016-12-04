
package com.github.mobile.accounts;

import java.io.IOException;

/**
 * Exception class to be thrown when server responds with a 401 and
 * an X-GitHub-OTP: required;:2fa-type header.
 * This exception wraps an {@link IOException} that is the actual exception
 * that occurred when the request was made.
 */
public class TwoFactorAuthException extends IOException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3889626691109709714L;

    /**
     * Cause exception
     */
    protected final IOException cause;

    /**
     * Two-factor authentication type
     */
    protected final int twoFactorAuthType;

    /**
     * Create two-factor authentification exception
     *
     * @param cause
     * @param twoFactorAuthType
     */
    public TwoFactorAuthException(IOException cause, int twoFactorAuthType) {
        this.cause = cause;
        this.twoFactorAuthType = twoFactorAuthType;
    }

    @Override
    public String getMessage() {
        return cause != null ? cause.getMessage() : super.getMessage();
    }

    @Override
    public IOException getCause() {
        return cause;
    }
}