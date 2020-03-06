package com.sloppylinux.mchl.util;


import org.apache.commons.text.StringEscapeUtils;

public final class Utils {

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
}
