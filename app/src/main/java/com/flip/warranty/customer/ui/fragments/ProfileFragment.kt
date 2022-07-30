package com.flip.warranty.customer.ui.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.flip.warranty.R
import com.flip.warranty.customer.rcvAdapter.BuyNowAdapter
import com.flip.warranty.customer.uitility.BuyNowClickInterface
import com.flip.warranty.customer.viewModel.BuyNowViewModel
import com.flip.warranty.databinding.FragmentProfileBinding
import com.flip.warranty.login.LoginPage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile), BuyNowClickInterface {
    lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var sharedPref: SharedPreferences

    val viewModel: BuyNowViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding.profile.nameTV.text = sharedPref.getString("email", "Email Not Available")
        "BlockChain Address :- ${
            sharedPref.getString(
                "blockChainAddress",
                "Block Chain Address Not Available"
            )
        }".also { binding.profile.emailTV.text = it }
        val colorList = resources.getIntArray(R.array.BgColorList)

        viewModel.productHistoryList.observe(viewLifecycleOwner) {
            binding.orderHistory.adapter = BuyNowAdapter(it, this, true)
            binding.progressBar.visibility = View.GONE
        }


        binding.LogOut.setOnClickListener {
            val v = sharedPref.edit().clear()
            v.apply()
            activity?.finishAffinity()
            startActivity(Intent(context, LoginPage::class.java))
        }
        binding.profile.cardView.setBackgroundColor(colorList[colorList.indices.random()])
    }

    override fun onClick(pos: Int) {

    }
}