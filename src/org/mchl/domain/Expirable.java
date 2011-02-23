package org.mchl.domain;

import java.io.Serializable;

public abstract class Expirable implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long expiration;
	
	/**
	 * Sets the expiration for this object
	 * @param expiration The expiration date in millis
	 */
	public void setExpiration(Long expiration)
	{
		this.expiration = expiration;
	}
	
	/**
	 * Gets the objects expiration date
	 * @return The expiration date in millis
	 */
	public Long getExpiration()
	{
		return expiration;
	}
	
	/**
	 * Method to determine if the object is expired
	 * @param current The current time in millis
	 * @return True if expired, false otherwise
	 */
	public boolean isExpired(long current)
	{
		return ((expiration != null) && (current > expiration));
	}

}
