package com.flip.warranty.customer

import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.flip.warranty.MainActivity
import com.flip.warranty.R
import com.flip.warranty.databinding.ActivityCustomerDashboardBinding

class CustomerDashboard : AppCompatActivity() {
    lateinit var binding: ActivityCustomerDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme.applyStyle(R.style.bottomAppBar, true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_dashboard)
        binding.changeProfile.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.background = null
        binding.bottomNavView.menu.getItem(1).isEnabled = false
        val animation = AnimationUtils.loadAnimation(this, R.anim.circle_explotion_anim).apply {
            duration = 600
            interpolator = AccelerateDecelerateInterpolator()
        }
        binding.addBtn.setOnClickListener {
            binding.title.text = "Warranty Check"
            binding.circleCover.startAnimation(animation) {
                //display fragment when animation is finished
                navController.navigate(R.id.warrantyScannerFragment)
                binding.circleCover.isVisible = false
            }

        }


        NavigationUI.setupWithNavController(binding.bottomNavView, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.purchaseFragment) {
                binding.addBtn.isClickable = true
                binding.title.text = "Buy Now"
                binding.Root.setBackgroundColor(resources.getColor(R.color.appBG))
            } else if (destination.id == R.id.profileFragment) {
                binding.addBtn.isClickable = true
                binding.title.text = "Profile"
                binding.Root.setBackgroundColor(resources.getColor(R.color.appBG))
            } else {
                binding.addBtn.isClickable = false
                binding.bottomNavView.isSelected = false
                binding.Root.setBackgroundColor(resources.getColor(R.color.MattPinkDarkBottom))
            }
        }
    }
}