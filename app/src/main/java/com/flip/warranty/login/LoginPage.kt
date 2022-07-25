package com.flip.warranty.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.flip.warranty.R
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
        binding.btnRetailer.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, RetailerDashboard::class.java))
        }

    }
}