package com.rathoreapps.marvelheros.network

import com.rathoreapps.marvelheros.dataModels.MarvelCharacter
import javax.inject.Inject

/**
 * Copyright (C) 2023 RathoreApps Inc.
 *
 * Created by Rahul Kumar
 * @Date: 09/12/23
 * @Time: 12:04 pm
 * @Email: rathoreapps01@gmail.com
 *
 * Description: Repository to make api calls.
 */
class MarvelRepository @Inject internal constructor (private val apiService: MarvelApiServices) {

    suspend fun getMarvelCharacters(): ResponseOf<List<MarvelCharacter>?> {
        return RetrofitResponse.makeApiCall {
            apiService.getMarvelCharacters()
        }
    }
}