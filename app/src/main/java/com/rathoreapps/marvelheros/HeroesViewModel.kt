package com.rathoreapps.marvelheros

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rathoreapps.marvelheros.dataModels.MarvelCharacter
import com.rathoreapps.marvelheros.network.APIClient
import com.rathoreapps.marvelheros.network.MarvelApiServices
import com.rathoreapps.marvelheros.network.MarvelRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Copyright (C) 2023 RathoreApps Inc.
 *
 * Created by Rahul Kumar
 * @Date: 09/12/23
 * @Time: 12:20 pm
 * @Email: rathoreapps01@gmail.com
 *
 * Description: View Model for Heroes list Activity
 */
class HeroesViewModel: ViewModel() {
    private val marvelCharacters = MutableLiveData<List<MarvelCharacter>>()
    private val apiErrorText = MutableLiveData<String>()

    private var repository: MarvelRepository = MarvelRepository(APIClient.client.create(MarvelApiServices::class.java))

    /**
     * This method return mutable list as Live Data
     * @return List of marvel characters
     * */
    fun getMarvelCharacters(): LiveData<List<MarvelCharacter>> {
        return marvelCharacters
    }

    /**
     * This method return error in case of api failure
     * @return API error text
     * */
    fun getErrorText(): LiveData<String> {
        return apiErrorText
    }

    /**
     * Fetch marvel characters from server
     * */
    fun fetchMarvelCharacters() {
        repository.getMarvelCharacters(object : Callback<List<MarvelCharacter>> {
            override fun onResponse(call: Call<List<MarvelCharacter>>, response: Response<List<MarvelCharacter>>) {
                if (response.isSuccessful) {
                    marvelCharacters.value = response.body()
                } else {
                    apiErrorText.value = "Something went wrong"
                }
            }

            override fun onFailure(call: Call<List<MarvelCharacter>>, t: Throwable) {
                apiErrorText.value = t.localizedMessage
            }
        })
    }
}
