package org.example.weatherprototype.network.connection

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    private val networkConnectionChecker: NetworkConnectionChecker
) : Interceptor {
    @Throws(NoConnectionException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkConnectionChecker.isNetworkConnected()) {
            throw NoConnectionException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}
