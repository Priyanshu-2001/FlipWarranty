package com.flip.warranty.login.di

import android.app.Application
import com.flip.warranty.login.repository.LoginRepositoryImpl
import com.flip.warranty.login.utility.LoginAPI
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
object LoginModule {

    @Singleton
    @Provides
    fun provideLoginApi(): LoginAPI {
        return Retrofit.Builder()
            .baseUrl(Globals.EndPoint)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(api: LoginAPI, app: Application): LoginRepositoryImpl {
        return LoginRepositoryImpl(api, app)
    }
}