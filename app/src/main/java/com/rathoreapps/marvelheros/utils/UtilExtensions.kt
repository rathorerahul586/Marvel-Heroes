package com.rathoreapps.marvelheros.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.rathoreapps.marvelheros.MarvelHeroesApplication
import com.rathoreapps.marvelheros.R

/**
 * Copyright (C) 2023 RathoreApps Inc.
 *
 * Created by Rahul Kumar
 * @Date: 09/12/23
 * @Time: 2:14 pm
 * @Email: rathoreapps01@gmail.com
 *
 * Description: Utility extension methods
 */

fun ImageView.setImageWithGlideCache(imageUrl: String, uniqueId: String) {
    Glide.with(context).load(imageUrl)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.placeholder)
                .signature(ObjectKey(uniqueId))
        )
        .into(this)
}

fun String.removeEscapeCharacters(): String {
    // Use regular expression to match any combination of \r, \n, \t
    val regex = Regex("[\\r\\n\\t]+")
    return regex.replace(this, "")
}

fun Int.dpToPixels(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

fun View.showHide(show: Boolean) {
    this.visibility = if (show) ViewGroup.VISIBLE else ViewGroup.GONE
}

fun getStringFromAppContext(@StringRes string: Int): String {
    return MarvelHeroesApplication.appContext.getString(string)
}

fun isNetworkConnected(): Boolean {
    val connectivityManager =
        MarvelHeroesApplication.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

    return networkCapabilities != null &&
            (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
}