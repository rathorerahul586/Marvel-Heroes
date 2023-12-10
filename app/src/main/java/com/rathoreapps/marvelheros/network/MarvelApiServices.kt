package com.rathoreapps.marvelheros.network

import com.rathoreapps.marvelheros.dataModels.MarvelCharacter
import retrofit2.Call
import retrofit2.http.GET

/**
 * Copyright (C) 2023 RathoreApps Inc.
 *
 * Created by Rahul Kumar
 * @Date: 09/12/23
 * @Time: 11:53 am
 * @Email: rathoreapps01@gmail.com
 *
 * Description: All the API related to this app will be write here.
 */

interface MarvelApiServices {
    @GET(EndpointConstants.GET_MARVEL_CHARACTERS)
    fun getMarvelCharacters(): Call<List<MarvelCharacter>>
}