package com.flip.warranty

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.flip.warranty.customer.ui.CustomerDashboard
import com.flip.warranty.databinding.ActivityMainBinding
import com.flip.warranty.retailer.ui.RetailerDashboard

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.buyer.animate().translationX(-20f)
        binding.retailer.animate().translationX(20f)
        binding.text.animate().translationY(600f).startDelay
        binding.retailer.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, RetailerDashboard::class.java))
        }
        binding.buyer.setOnClickListener {
            finishAffinity()
//TODO
//            if(loggedin){
//                take him to customerDashBoard
//            }else{
//                make him loggin
//            }

            startActivity(Intent(this, CustomerDashboard::class.java))
        }
    }
}