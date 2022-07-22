package com.flip.warranty.retailer.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.flip.warranty.LoginPage
import com.flip.warranty.R
import com.flip.warranty.databinding.ActivityRetailerDashboardBinding
import com.flip.warranty.retailer.ui.bottomSheet.AddNewProductForm

class RetailerDashboard : AppCompatActivity() {
    lateinit var binding: ActivityRetailerDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retailer_dashboard)

        binding.changeProfile.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
        }

        binding.addBtn.setOnClickListener {
            val addProductBottomSheet = AddNewProductForm()
            addProductBottomSheet.show(supportFragmentManager, "addProductBottomSheet")
        }
        binding.addBtn.animate().translationY(-30f).interpolator.getInterpolation(10f)
        binding.addBtn.animate().translationX(-30f).interpolator.getInterpolation(10f)
    }
}