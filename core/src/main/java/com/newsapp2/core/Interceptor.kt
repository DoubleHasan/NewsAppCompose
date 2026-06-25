package com.newsapp2.core

import okhttp3.Interceptor
import okhttp3.Response

class NewsInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url =
            originalRequest.url.newBuilder().addQueryParameter("apiKey", apikey.API_KEY).build()
        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}