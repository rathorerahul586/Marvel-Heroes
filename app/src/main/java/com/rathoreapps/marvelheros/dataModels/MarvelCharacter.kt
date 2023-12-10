package com.rathoreapps.marvelheros.dataModels

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Copyright (C) 2023 RathoreApps Inc.
 *
 * Created by Rahul Kumar
 * @Date: 09/12/23
 * @Time: 11:55 am
 * @Email: rathoreapps01@gmail.com
 *
 * Description: Data model for Marvel Character
 */

@Keep
data class MarvelCharacter(
    @SerializedName("name")
    val name: String,
    @SerializedName("realname")
    val realName: String,
    @SerializedName("team")
    val team: String,
    @SerializedName("firstappearance")
    val firstAppearance: String,
    @SerializedName("createdby")
    val createdBy: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("imageurl")
    val imageUrl: String,
    @SerializedName("bio")
    val bio: String
)
