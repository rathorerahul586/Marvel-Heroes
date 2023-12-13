package com.rathoreapps.marvelheros.network

import com.rathoreapps.marvelheros.R
import com.rathoreapps.marvelheros.network.ResponseOf.RandomFailure
import com.rathoreapps.marvelheros.network.ResponseOf.ServerFailure
import com.rathoreapps.marvelheros.utils.isNetworkConnected
import retrofit2.Response
import java.io.IOException

/**
 * Copyright (C) 2023 RathoreApps Inc.
 *
 * Created by Rahul Kumar
 * Date: 12.12.2023
 * Time: 10:30 pm
 * Email: rathoreapps01@gmail.com
 *
 * Description: RetrofitResponse object class to make api call all over the application.
 * [RetrofitResponse] is responsible for handling final API response.
 */

object RetrofitResponse {

    /**
     * Use this method to make the retrofit api call which will return retrofit [Response].
     * Declare the api call in the lambda function.
     */
    suspend fun <T> makeApiCall(apiCall: (suspend () -> Response<T>?)): ResponseOf<T?> {
        return try {
            if (isNetworkConnected()) {
                handleApiResponse(apiCall.invoke())
            } else {
                RandomFailure(R.string.no_internet)
            }
        } catch (e: Exception) {
            return when (e) {
                is IOException -> {
                    RandomFailure(R.string.no_internet)
                }

                is IllegalArgumentException -> {
                    RandomFailure(R.string.error_something_went_wrong)
                }

                else -> {
                    RandomFailure(R.string.error_something_went_wrong)
                }
            }
        }
    }

    private fun <T> handleApiResponse(response: Response<T>?): ResponseOf<T?> {
        return response?.let { mResponse ->
            if (mResponse.isSuccessful) {
                ResponseOf.Success(mResponse.body())
            } else {
                ServerFailure(
                    mResponse.errorBody(),
                    mResponse.message()
                )
            }
        } ?: run {
            RandomFailure(R.string.error_something_went_wrong)
        }
    }
}
