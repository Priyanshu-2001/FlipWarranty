package com.flip.warranty.retailer.di

import android.app.Application
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
    fun providesRepositoryImpl(api: AddNewProductApi, app: Application): AddNewProductImpl {
        return AddNewProductImpl(api, app)
    }

}