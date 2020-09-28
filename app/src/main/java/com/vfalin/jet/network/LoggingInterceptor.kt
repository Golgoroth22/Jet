package com.vfalin.jet.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import timber.log.Timber

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        Timber.i("Sending request ${request.url()} on ${chain.connection()} + ${request.headers()}")
        Timber.i("Sending request body\n${requestBodyTo(request)}")
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        Timber.i(
            "Received response for ${response.request()
                .url()} in ${t2 - t1} + ${response.headers()}"
        )
        Timber.i("Received response body\n${response.body()}")
        return response
    }

    private fun requestBodyTo(request: Request): String {
        val buffer = Buffer()
        request.newBuilder().build().body()?.writeTo(buffer)
        return buffer.readUtf8()
    }
}