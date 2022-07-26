package com.flip.warranty.customer.ui.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.flip.warranty.R
import com.flip.warranty.databinding.FragmentProfileBinding
import com.flip.warranty.login.LoginPage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding.profile.nameTV.text = sharedPref.getString("email", "Email Not Available")
        binding.profile.emailTV.text =
            sharedPref.getString("blockChainAddress", "Block Chain Address Not Available")
        val colorList = resources.getIntArray(R.array.BgColorList)

        binding.LogOut.setOnClickListener {
            val v = sharedPref.edit().clear()
            v.apply()
            activity?.finishAffinity()
            startActivity(Intent(context, LoginPage::class.java))
        }
        binding.profile.cardView.setBackgroundColor(colorList[colorList.indices.random()])
    }
}