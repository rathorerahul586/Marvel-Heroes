package com.rathoreapps.marvelheros.dagger

import com.rathoreapps.marvelheros.network.APIClient
import com.rathoreapps.marvelheros.network.MarvelApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Copyright (C) 2023 RathoreApps Inc.
 *
 * Created by Rahul Kumar
 * @Date: 12/12/23
 * @Time: 6:40 pm
 * @Email: rathoreapps01@gmail.com
 *
 * Description: Dagger module to initialize dependency.
 */

@Module
@InstallIn(SingletonComponent::class)
object DaggerModule {
    @Singleton
    @Provides
    fun provideAPIService(): MarvelApiServices {
        return APIClient.client.create(MarvelApiServices::class.java)
    }
}