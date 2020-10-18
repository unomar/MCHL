package com.sloppylinux.mchl.util;

import java.util.HashMap;
import java.util.Map;

public class TemporaryCache
{
    Map<String, Object> cacheMap = new HashMap<>();

    private static final TemporaryCache instance = new TemporaryCache();

    private TemporaryCache()
    {

    }

    public static TemporaryCache getInstance()
    {
        return instance;
    }

    public void put(String key, Object value)
    {
        cacheMap.put(key, value);
    }

    public Object get(String key)
    {
        return cacheMap.get(key);
    }
}
