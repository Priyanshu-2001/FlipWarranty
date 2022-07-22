package com.flip.warranty

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.flip.warranty.customer.ui.CustomerDashboard
import com.flip.warranty.databinding.ActivityLoginPageBinding
import com.flip.warranty.retailer.ui.RetailerDashboard

class LoginPage : AppCompatActivity() {
    lateinit var binding: ActivityLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_page)
        binding.btnSignIn.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, CustomerDashboard::class.java))
        }
        binding.btnRetailer.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, RetailerDashboard::class.java))
        }

    }
}