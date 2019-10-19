package com.sloppylinux.mchl.util;

import android.content.Context;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Date;

import com.sloppylinux.mchl.domain.Expirable;

/**
 * Utility class to read & write serializable classes out to permanent storage
 * for use as cache.
 * 
 */
public final class ObjectCache
{
	private static final String ext = ".mchl";
	private static final String TAG = "ObjectCache";
	private static final long timeToLive = 86400000l; // One day...customizable?

	/**
	 * Private Constructor to discourage instantiation
	 */
	private ObjectCache()
	{
	}


	/**
	 * Reads an object from the cache. This should only be used after isCached
	 * has confirmed that the object is cached and still current.
	 * 
	 * @param fileName
	 *            The filename containing the cache
	 * @return The object or null if not previously cached
	 */
	public static Object readCache(String fileName, Context c)
	{
		fileName = convertFilename(fileName);
		Object retVal = null;
		
		FileInputStream f_in = null;
		ObjectInputStream obj_in = null;
		try
		{
			f_in = c.openFileInput(fileName);

			// Read object using ObjectInputStream
			obj_in = new ObjectInputStream(f_in);

			// Read an object
			retVal = obj_in.readObject();
		}
		catch (FileNotFoundException e)
		{
			Log.e(TAG, "FileNotFoundException: " + fileName);
			retVal = null;
		}
		catch (StreamCorruptedException e)
		{
			Log.e(TAG, "StreamCorruptedException: " + fileName);
			retVal = null;
		}
		catch (IOException e)
		{
			Log.e(TAG, "IOExceptionException: " + fileName);
			retVal = null;
		}
		catch (ClassNotFoundException e)
		{
			Log.e(TAG, "ClassNotFoundException: " + fileName);
			retVal = null;
		}
		finally
		{
			try
			{
				if (f_in != null)
				{
					f_in.close();
				}
				if (obj_in != null)
				{
					obj_in.close();
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, "Caught exception closing output streams");
			}
		}
		
		if (retVal instanceof Expirable)
		{
			Expirable exp = (Expirable) retVal;
			if (exp.isExpired(new Date().getTime()))
			{
				retVal = null;
			}
		}
		return retVal;
	}

	/**
	 * Write an object out to disk cache
	 * 
	 * @param fileName
	 *            The filename in which to store the object
	 * @param obj
	 *            The *Serializable* object to store in cache
	 * @return True if successful, otherwise false
	 */
	public static void writeCache(String fileName, Expirable obj, Context c)
	{
		fileName = convertFilename(fileName);
		FileOutputStream f_out = null;
		ObjectOutputStream obj_out = null;
		try
		{
			// Write to disk with FileOutputStream
			f_out = c.openFileOutput(fileName, Context.MODE_PRIVATE);

			// Write object with ObjectOutputStream
			obj_out = new ObjectOutputStream(f_out);
			
			// Set the objects expiration
			obj.setExpiration(new Date().getTime() + timeToLive);

			// Write object out to disk
			obj_out.writeObject(obj);
		}
		catch (FileNotFoundException e)
		{
			Log.e(TAG, "FileNotFoundException: " + fileName);
		}
		catch (IOException e)
		{
			Log.e(TAG, "IOException: " + e.getMessage());
		}
		finally
		{
			try
			{
				if (f_out != null)
				{
					f_out.close();
				}
				if (obj_out != null)
				{
					obj_out.close();
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, "Caught exception closing output streams");
			}
		}
	}
	
	private static String convertFilename(String input)
	{
		return input.replace(" ", "_") + ext;
	}
}
