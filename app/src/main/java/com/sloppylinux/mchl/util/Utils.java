package com.sloppylinux.mchl.util;

public class Utils {

    public static final String convertString(String value)
    {
        if (value == null)
        {
            return "";
        }
        else {
            return value.replaceAll("[\\u2018\\u2019]", "'")
                    .replaceAll("[\\u201C\\u201D]", "\"");
        }
    }

    /**
     * Get a value restricted to a maximum number of chars
     * @param value The value to retireve
     * @param maxChars The maximum number of chars
     * @return The string
     */
    public static final String getFormatted(String value, int maxChars)
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
