package com.sloppylinux.mchl.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class Expirable implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long expiration;

    /**
     * Gets the objects expiration date
     *
     * @return The expiration date in millis
     */
    public Long getExpiration()
    {
        return expiration;
    }

    /**
     * Sets the expiration for this object
     *
     * @param expiration The expiration date in millis
     */
    public void setExpiration(Long expiration)
    {
        this.expiration = expiration;
    }

    /**
     * Method to determine if the object is expired.
     * @return True if expired
     */
    public boolean isExpired()
    {
        return isExpired(new Date().getTime());
    }

    /**
     * Method to determine if the object is expired
     *
     * @param expirationTime The expiration time in millis
     * @return True if expired, false otherwise
     */
    public boolean isExpired(long expirationTime)
    {
        return ((expiration != null) && (expirationTime > expiration));
    }

}
