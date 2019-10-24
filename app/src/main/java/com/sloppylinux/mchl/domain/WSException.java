package com.sloppylinux.mchl.domain;

public class WSException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * Default Constructor
     *
     * @param message The message
     * @param e       A throwable exception
     */
    public WSException(String message, Throwable e)
    {
        super(message, e);
    }

    /**
     * Default Constructor with no exception
     *
     * @param message The message
     */
    public WSException(String message)
    {
        super(message);
    }

}
