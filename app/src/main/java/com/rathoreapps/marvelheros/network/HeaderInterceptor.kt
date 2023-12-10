package com.rathoreapps.marvelheros.network

import android.util.Log
import okhttp3.*
import java.io.IOException
import java.util.*

/**
 * Copyright (C) 2023 RathoreApps Inc.
 *
 * Created by Rahul Kumar
 * @Date: 09/12/23
 * @Time: 11:40 am
 * @Email: rathoreapps01@gmail.com
 *
 * Description: Header Interceptor for retrofit to handle response and API logs
 */

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val mainUrl: HttpUrl = original.url
        Log.i(TAG, "mainUrl mainUrl mainUrl: $mainUrl")
        Log.i(TAG, "mainUrl mainUrl encodedPath: " + mainUrl.encodedPath)
        Log.i(TAG, "mainUrl mainUrl host: " + mainUrl.host)
        val requestBuilder: Request.Builder = original.newBuilder()
        val request: Request = requestBuilder.build()
        val url: String = request.url.toString()
        val response: Response = chain.proceed(request)
        if (response.code == 401) {
            try {
                Log.e(TAG, "intercept: " + "login failed ${response.code}...")
            } catch (e: Exception) {
                Log.e(TAG, "intercept: " + "login failed... ${e.message}")
            }
        } else {
            if (response.isSuccessful) {
                handleSuccess(response)
            } else {
                handleError(IOException(), url)
            }
        }
        return response
    }

    private fun handleError(e: IOException, service: String) {
        Log.d(TAG, e.toString())
        Log.d(TAG, "endService error " + service + " : " + Date().time)
    }

    private fun handleSuccess(response: Response) {
        if (response.body != null) {
            val responseString: String = response.body.toString()
            Log.d(
                "http", ("endService "
                        + response.request.url) + " : "
                        + Date().time
                        + ":"
                        + responseString
            )
        }
    }

    companion object {
        val TAG: String = HeaderInterceptor::class.java.simpleName
    }
}