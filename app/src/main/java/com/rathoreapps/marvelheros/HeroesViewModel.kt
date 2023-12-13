package com.rathoreapps.marvelheros

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rathoreapps.marvelheros.dataModels.MarvelCharacter
import com.rathoreapps.marvelheros.network.MarvelRepository
import com.rathoreapps.marvelheros.network.handleRandomFailure
import com.rathoreapps.marvelheros.network.handleServerFailure
import com.rathoreapps.marvelheros.network.handleSuccess
import com.rathoreapps.marvelheros.utils.getStringFromAppContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

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

@HiltViewModel
class HeroesViewModel @Inject constructor() : ViewModel() {
    private val marvelCharacters = MutableLiveData<List<MarvelCharacter>>()
    private val apiErrorText = MutableLiveData<String>()

    @Inject
    lateinit var repository: MarvelRepository

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
        viewModelScope.launch {
            repository.getMarvelCharacters().let {
                it.handleSuccess { allCharacters ->
                    marvelCharacters.value = allCharacters
                }
                it.handleRandomFailure { errorMsg ->
                    errorMsg?.let { errorResId ->
                        apiErrorText.value = getStringFromAppContext(errorResId)
                    }
                }
                it.handleServerFailure { errorMessage ->
                    apiErrorText.value = errorMessage
                }
            }
        }
    }
}
