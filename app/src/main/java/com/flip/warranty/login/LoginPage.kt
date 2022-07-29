package com.flip.warranty.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.flip.warranty.R
import com.flip.warranty.customer.ui.CustomerDashboard
import com.flip.warranty.databinding.ActivityLoginPageBinding
import com.flip.warranty.login.repository.LoginRepositoryImpl
import com.flip.warranty.login.utility.LoginCred
import com.flip.warranty.login.utility.Validation
import com.flip.warranty.retailer.ui.RetailerDashboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginPage : AppCompatActivity() {
    lateinit var binding: ActivityLoginPageBinding

    @Inject
    lateinit var repositoryImpl: LoginRepositoryImpl

    override fun onStart() {
        super.onStart()
        try {
            val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
            val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
            val securePreferences = EncryptedSharedPreferences.create(
                "Main_File",
                mainKeyAlias,
                this,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            if (securePreferences.contains("token")) {
                val i: Intent = if (securePreferences.getString("userType", "normal") == "admin") {
                    Intent(this, RetailerDashboard::class.java)

                } else {
                    Intent(this, CustomerDashboard::class.java)
                }
                startActivity(i)
                finishAffinity()
            }
        } catch (E: Exception) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_page)

        binding.btnSignIn.setOnClickListener {
            if (Validation.validateUsername(binding.editUsername) && Validation.validatePassword(
                    binding.editPass
                )
            ) {
                binding.progressBar.visibility = View.VISIBLE
                MainScope().launch {
                    val v = repositoryImpl.LoginUser(
                        LoginCred(
                            email = binding.editUsername.text.toString(),
                            password = binding.editPass.text.toString()
                        )
                    )
                    if (v) {
                        super.finish()
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}