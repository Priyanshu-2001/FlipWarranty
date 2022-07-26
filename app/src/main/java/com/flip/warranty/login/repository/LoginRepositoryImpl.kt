package com.flip.warranty.login.repository

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.flip.warranty.customer.ui.CustomerDashboard
import com.flip.warranty.login.utility.LoginAPI
import com.flip.warranty.login.utility.LoginCred
import com.flip.warranty.retailer.ui.RetailerDashboard

class LoginRepositoryImpl(private val api: LoginAPI, val app: Application) : LoginRepository {

    override suspend fun LoginUser(cred: LoginCred): Boolean {
        val response = api.Login(cred)
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        if (response.isSuccessful) {
            val securePreferences = EncryptedSharedPreferences.create(
                "Main_File",
                mainKeyAlias,
                app.applicationContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            response.body()?.apply {
                with(securePreferences.edit()) {
                    putString("token", token)
                    putString("id", details._id)
                    putString("email", details.email)
                    putString("hash", details.hash)
                    putString("blockChainAddress", details.user_blockchain_account_address)
                    putString("salt", details.salt)
                    putInt("__v", details.__v)
                    putString("userType", details.user_type)
                    commit()
                }

                if (app.applicationContext is Activity) {
                    (app.applicationContext as Activity).finish()
                }
                if (details.user_type == "admin") {
                    val i = Intent(app, RetailerDashboard::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    app.startActivity(i)
                } else {
                    val i = Intent(app, CustomerDashboard::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    app.startActivity(i)
                }
            }
            return true
        } else {
            Toast.makeText(app.applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
            return false
        }
    }
}