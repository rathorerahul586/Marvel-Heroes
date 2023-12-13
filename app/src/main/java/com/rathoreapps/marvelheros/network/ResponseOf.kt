package com.rathoreapps.marvelheros.network

import androidx.annotation.StringRes
import com.rathoreapps.marvelheros.R
import com.rathoreapps.marvelheros.network.ResponseOf.RandomFailure
import com.rathoreapps.marvelheros.network.ResponseOf.ServerFailure
import okhttp3.ResponseBody

/**
 * Copyright (C) 2023 RathoreApps Inc.
 *
 * Created by Rahul Kumar
 * Date: 12.12.2023
 * Time: 10:10 pm
 * Email: rathoreapps01@gmail.com
 *
 * Description: Sealed class to to wrap network response. Use [ServerFailure] where we have
 * error message from the server. Use [RandomFailure] wherever we don't get the error from the
 * server or parsable error.
 */
sealed class ResponseOf<out T> {
    data class Success<out R>(val value: R) : ResponseOf<R>()

    data class ServerFailure(
        val errorBody: ResponseBody?,
        val errorMessage: String? = null
    ) : ResponseOf<Nothing>()

    data class RandomFailure(
        @StringRes
        val message: Int? = R.string.error_something_went_wrong
    ) : ResponseOf<Nothing>()
}
