package com.flip.warranty.customer.di

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.flip.warranty.customer.APIs.WarrantyDetailsAPI
import com.flip.warranty.customer.reopsitory.GetWarrantyRepositoryImpl
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
object CustomerModule {
    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        return EncryptedSharedPreferences.create(
            "Main_File",
            mainKeyAlias,
            app,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun providesWarrantyApi(): WarrantyDetailsAPI = Retrofit.Builder()
        .baseUrl(Globals.EndPoint)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WarrantyDetailsAPI::class.java)


    @Provides
    @Singleton
    fun providesWarrantyDetailsRepositoryImpl(
        api: WarrantyDetailsAPI,
        app: Application,
        pref: SharedPreferences
    ): GetWarrantyRepositoryImpl {
        return GetWarrantyRepositoryImpl(api, app, pref)
    }

}