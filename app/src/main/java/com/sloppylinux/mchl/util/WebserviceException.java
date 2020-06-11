package com.sloppylinux.mchl.util;

/**
 * A custom wrapper for WebService Exceptions.
 */
public class WebserviceException extends Exception
{
    public static final String ERROR_PREFIX = "ERROR: ";
    private final String userMessage;

    /**
     * Constructor.
     *
     * @param userMessage  The message to pass along to the user.
     * @param debugMessage The debugging error message
     * @param e            The exception to wrap
     */
    public WebserviceException(String userMessage, String debugMessage, Exception e)
    {
        super(debugMessage, e);
        this.userMessage = userMessage;
    }

    /**
     * Get the error message to display to the user.
     *
     * @return The user error message
     */
    public String getUserMessage()
    {
        return ERROR_PREFIX + userMessage;
    }
}
