package com.sloppylinux.mchl.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UtilsTest
{

    @Test
    public void testConvertString()
    {
        String nonUtf8 = "Wednesday Night Boy&#8217;s Club";
        String expected = "Wednesday Night Boy’s Club";

        String converted = Utils.convertString(nonUtf8);
        assertThat("Convert string did not remove non-UTF8 codes", converted, is(expected));
    }
}
