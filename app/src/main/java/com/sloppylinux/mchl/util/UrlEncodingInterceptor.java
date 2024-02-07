package com.sloppylinux.mchl.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;

import okhttp3.Response;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;

public class UrlEncodingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException
        {
            Request request = chain.request();
            try {
                Request original = chain.request();
                String url = original.url().toString();
                url = URLDecoder.decode(url, StandardCharsets.UTF_8.name());
                Builder requestBuilder = original.newBuilder().url(url);
                request = requestBuilder.build();
            }
            catch (UnsupportedEncodingException e)
            {

            }
            return chain.proceed(request);
        }
}