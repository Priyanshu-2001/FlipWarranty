package com.flip.warranty.retailer.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.flip.warranty.R
import com.flip.warranty.databinding.ActivityRetailerDashboardBinding
import com.flip.warranty.login.LoginPage
import com.flip.warranty.retailer.ui.bottomSheet.AddNewProductForm
import com.flip.warranty.retailer.viewModel.RetailerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RetailerDashboard : AppCompatActivity() {
    lateinit var binding: ActivityRetailerDashboardBinding
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retailer_dashboard)
        val securePreferences = EncryptedSharedPreferences.create(
            "Main_File",
            mainKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        binding.changeProfile.setOnClickListener {
            val v = securePreferences.edit().clear()
            v.apply()
            finishAffinity()
            startActivity(Intent(this, LoginPage::class.java))
        }

        val viewModel: RetailerViewModel by viewModels()

        binding.addBtn.setOnClickListener {
            val addProductBottomSheet = AddNewProductForm()
            addProductBottomSheet.show(supportFragmentManager, "addProductBottomSheet")
        }
        binding.addBtn.animate().translationY(-30f).interpolator.getInterpolation(10f)
        binding.addBtn.animate().translationX(-30f).interpolator.getInterpolation(10f)
    }
}