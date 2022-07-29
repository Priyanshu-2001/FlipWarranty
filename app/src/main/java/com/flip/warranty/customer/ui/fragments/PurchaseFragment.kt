package com.flip.warranty.customer.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.flip.warranty.R
import com.flip.warranty.customer.rcvAdapter.BuyNowAdapter
import com.flip.warranty.customer.uitility.BuyNowClickInterface
import com.flip.warranty.customer.viewModel.BuyNowViewModel
import com.flip.warranty.databinding.FragmentPurchaseBinding
import com.flip.warranty.utility.Globals.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class PurchaseFragment : Fragment(R.layout.fragment_purchase), BuyNowClickInterface {

    lateinit var binding: FragmentPurchaseBinding
    val viewModel: BuyNowViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPurchaseBinding.bind(view)

        lifecycleScope.launchWhenCreated {
            delay(1000)
            showProducts()
        }
    }

    private fun showProducts() {
        viewModel.productList.observeForever {
            binding.buyNowRCV.adapter = BuyNowAdapter(it, this)
            binding.progressBar.visibility = View.GONE
            Log.e(TAG, "showProducts: " + it.size)
        }
    }

    override fun onClick(pos: Int) {
        viewModel.buyItem(pos)
    }
}