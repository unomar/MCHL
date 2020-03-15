package com.sloppylinux.mchl.util;


import org.apache.commons.text.StringEscapeUtils;

import java.util.logging.Logger;

public final class Utils {
    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    public static String convertString(String value)
    {
        if (value == null)
        {
            return "";
        }
        else {
            return StringEscapeUtils.unescapeHtml4(value);
        }
    }

    /**
     * Get a value restricted to a maximum number of chars
     * @param value The value to retireve
     * @param maxChars The maximum number of chars
     * @return The string
     */
    public static String getFormatted(String value, int maxChars)
    {
        String converted = convertString(value);
        if (converted.length() > maxChars) {
            String format = "%." + (maxChars - 3) + "s...";
            return String.format(format, converted);
        }
        else
        {
            return converted;
        }
    }

    /**
     * Safely parse a String value as an integer.
     * @param string The string to parse
     * @return The integer value or -1 if invalid
     */
    public static int safeParseInt(String string)
    {
        int retVal = -1;
        if (string != null)
        {
            try
            {
                retVal = Integer.parseInt(string);
            }
            catch (NumberFormatException e)
            {
                LOG.fine("Unable to parse int from: " + string);
            }
        }
        return retVal;
    }
}
