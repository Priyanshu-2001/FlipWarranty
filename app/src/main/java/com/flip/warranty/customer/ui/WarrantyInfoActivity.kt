package com.flip.warranty.customer.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.flip.warranty.R
import com.flip.warranty.customer.viewModel.WarrantyDetailsViewModel
import com.flip.warranty.databinding.ActivityWarrantyInfoBinding
import com.flip.warranty.utility.Globals.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class WarrantyInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityWarrantyInfoBinding
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_warranty_info)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        val viewModel: WarrantyDetailsViewModel by viewModels()
        lifecycleScope.launchWhenCreated {
            delay(1000)
            showDetails(viewModel)
        }
        viewModel.serialNumber.value = intent.getStringExtra("serialNumber")
        val colorList = resources.getIntArray(R.array.BgColorList)
        Log.e("TAG", "onCreate size : ${colorList[1]}")
        binding.warrantyCard.cardView.setBackgroundColor(colorList[colorList.indices.random()])
    }

    private fun showDetails(viewModel: WarrantyDetailsViewModel) {
        try {
            viewModel.warrantyDetails.observe(this) {
                Log.e(TAG, "showDetails: ${it.end_date}")
                binding.warrantyCard.EndDate.text = changeDateFormatFromUnixTReadable(it.end_date)
                binding.warrantyCard.startDate.text =
                    changeDateFormatFromUnixTReadable(it.start_date)
                binding.warrantyCard.remark.text = it.warranty_terms_and_conditions
                binding.warrantyCard.priceProgressBar.visibility = View.GONE
                animateAll()
            }
        } catch (E: Exception) {
            E.printStackTrace()
        }
    }

    private fun changeDateFormatFromUnixTReadable(endDate: String): CharSequence? {
        val dv = endDate.toLong() * 1000
        val df = Date(dv)
        return SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(df)
    }

    private fun animateAll() {
        binding.warrantyCard.apply {
            EndDate.animate().alpha(1.00f)
            EndDateTV.animate().alpha(1.00f)
            remark.animate().alpha(1.00f)
            RemarkTv.animate().alpha(1.00f)
            startDate.animate().alpha(1.00f)
            startTV.animate().alpha(1.00f)
        }
    }

}