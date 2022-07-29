package com.flip.warranty.retailer.di

import android.content.SharedPreferences
import com.flip.warranty.customer.APIs.GetSerialNumberListApi
import com.flip.warranty.retailer.api.AddNewProductApi
import com.flip.warranty.retailer.repositoryImpl.AddNewProductImpl
import com.flip.warranty.utility.Globals
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetailerModule {

    @Provides
    @Singleton
    fun providesApi(): AddNewProductApi = Retrofit.Builder()
        .baseUrl(Globals.EndPoint)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AddNewProductApi::class.java)

    @Provides
    @Singleton
    fun providesRepositoryImpl(
        api: AddNewProductApi,
        sharedPreferences: SharedPreferences,
        serialListApi: GetSerialNumberListApi
    ): AddNewProductImpl {
        return AddNewProductImpl(api, sharedPreferences, serialListApi)
    }

}