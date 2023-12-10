package com.rathoreapps.marvelheros.network

import com.rathoreapps.marvelheros.dataModels.MarvelCharacter
import retrofit2.Call
import retrofit2.Callback

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
class MarvelRepository(private val apiService: MarvelApiServices) {

    fun getMarvelCharacters(callback: Callback<List<MarvelCharacter>>) {
        val call: Call<List<MarvelCharacter>> = apiService.getMarvelCharacters()
        call.enqueue(callback)
    }
}