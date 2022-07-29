package com.flip.warranty.retailer.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.flip.warranty.R
import com.flip.warranty.customer.uitility.BuyNowClickInterface
import com.flip.warranty.databinding.ActivityRetailerDashboardBinding
import com.flip.warranty.login.LoginPage
import com.flip.warranty.retailer.rcvAdapter.MainRetailerRcvAdapter
import com.flip.warranty.retailer.ui.bottomSheet.AddNewProductForm
import com.flip.warranty.retailer.viewModel.RetailerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class RetailerDashboard : AppCompatActivity(), BuyNowClickInterface {
    lateinit var binding: ActivityRetailerDashboardBinding

    @Inject
    lateinit var securePreferences: SharedPreferences
    val viewModel: RetailerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retailer_dashboard)
        binding.changeProfile.setOnClickListener {
            val v = securePreferences.edit().clear()
            v.apply()
            finishAffinity()
            startActivity(Intent(this, LoginPage::class.java))
        }

        lifecycleScope.launchWhenCreated {
            delay(1000)
            addProductsToRCVs()
        }

        binding.addBtn.setOnClickListener {
            val addProductBottomSheet = AddNewProductForm()
            addProductBottomSheet.show(supportFragmentManager, "addProductBottomSheet")
        }
        binding.addBtn.animate().translationY(-30f).interpolator.getInterpolation(10f)
        binding.addBtn.animate().translationX(-30f).interpolator.getInterpolation(10f)

    }

    private fun addProductsToRCVs() {
        viewModel.productListUnsold.observeForever {
            binding.listedRCV.adapter = MainRetailerRcvAdapter(this, it)
        }
        viewModel.productListUnSigned.observeForever {
            binding.unsignedRCV.adapter = MainRetailerRcvAdapter(this, it)
        }
        viewModel.productListSold.observeForever {
            binding.signedRCV.adapter = MainRetailerRcvAdapter(this, it)
            binding.progressBar.visibility = View.GONE
            binding.listedItems.visibility = View.VISIBLE
            binding.unsignedItems.visibility = View.VISIBLE
            binding.textView3.visibility = View.VISIBLE
        }
    }


    override fun onClick(pos: Int) {
        TODO("Not yet implemented")
    }

}