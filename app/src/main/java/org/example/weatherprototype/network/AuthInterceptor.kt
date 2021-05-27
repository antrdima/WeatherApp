package org.example.weatherprototype.network

import okhttp3.Interceptor
import okhttp3.Response
import org.example.weatherprototype.BuildConfig
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("appid", BuildConfig.OPEN_WEATHER_API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}