package com.flip.warranty.retailer.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.flip.warranty.R
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.databinding.ActivityRetailerDashboardBinding
import com.flip.warranty.login.LoginPage
import com.flip.warranty.retailer.rcvAdapter.MainRetailerRcvAdapter
import com.flip.warranty.retailer.ui.bottomSheet.AddNewProductForm
import com.flip.warranty.retailer.utility.onClickItemRetailer
import com.flip.warranty.retailer.viewModel.RetailerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class RetailerDashboard : AppCompatActivity(), onClickItemRetailer {
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

        binding.swipe2Refresh.setOnRefreshListener {
            viewModel.loadFunc()
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
        ViewCompat.setNestedScrollingEnabled(binding.listedRCV, false)
        ViewCompat.setNestedScrollingEnabled(binding.signedRCV, false)
        ViewCompat.setNestedScrollingEnabled(binding.unsignedItems, false)
        viewModel.productListUnsold.observeForever {
            binding.listedRCV.adapter = MainRetailerRcvAdapter(this, it, 1)
        }
        viewModel.productListUnSigned.observeForever {
            binding.unsignedRCV.adapter = MainRetailerRcvAdapter(this, it, 2)
        }
        viewModel.productListSigned.observeForever {
            binding.signedRCV.adapter = MainRetailerRcvAdapter(this, it, 3)
            binding.progressBar.visibility = View.GONE
            binding.listedItems.visibility = View.VISIBLE
            binding.unsignedItems.visibility = View.VISIBLE
            binding.textView3.visibility = View.VISIBLE
            binding.swipe2Refresh.isRefreshing = false
        }
    }

    override fun onclick(pos: Int, data: ProductDetailsData, type: Int) {
        when (type) {
            1 ->
                viewModel.clickOnlisted(pos, data)
            2 -> {
                viewModel.clickOnUnsigned(pos, data)
            }
            3 ->
                viewModel.clickOnSigned(pos, data)
        }
    }

}