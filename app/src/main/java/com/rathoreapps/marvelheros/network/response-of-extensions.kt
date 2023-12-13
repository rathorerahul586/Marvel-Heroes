package com.rathoreapps.marvelheros.network

import com.rathoreapps.marvelheros.R
import com.rathoreapps.marvelheros.utils.getStringFromAppContext

/**
 * Copyright (C) 2023 RathoreApps Inc.
 *
 * Created by Rahul Kumar
 * Date: 12.12.2023
 * Time: 9:42 pm
 * Email: rathoreapps01@gmail.com
 *
 *Description: Extensions to use with ResponseOf class to reduce boilerplate code while handling network responses
 */

inline fun <reified T> ResponseOf<T>.handleSuccess(callback: (value: T) -> Unit) {
    if (this is ResponseOf.Success) {
        callback(value)
    }
}

inline fun <reified T> ResponseOf<T>.handleServerFailure(callback: (error: String?) -> Unit) {
    if (this is ResponseOf.ServerFailure) {
        callback(
            errorMessage?.let {
                errorMessage
            } ?: run {
                getStringFromAppContext(R.string.error_something_went_wrong)
            }
        )
    }
}

inline fun <reified T> ResponseOf<T>.handleRandomFailure(callback: (error: Int?) -> Unit) {
    if (this is ResponseOf.RandomFailure) {
        callback(message)
    }
}
