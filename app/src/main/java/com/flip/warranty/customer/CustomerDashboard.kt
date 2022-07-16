package com.flip.warranty.customer

import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
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
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
        }
        binding.warrantyCheck.setOnClickListener {
            binding.title.text = "Warranty Check"
            binding.warrantyCheck.isClickable = false
            binding.bottomNavView.menu.getItem(1).isChecked = true
            binding.circleCover.startAnimation(animation) {
                //display fragment when animation is finished
                navController.navigate(R.id.warrantyScannerFragment)
                binding.circleCover.isVisible = false
            }

        }


//        NavigationUI.setupWithNavController(binding.bottomNavView, navController)
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.purchaseFragment -> {
                    navController.popBackStack(R.id.purchaseFragment, true)
                    navController.clearBackStack(R.id.purchaseFragment)
                    navController.navigate(R.id.purchaseFragment)
                }
                R.id.profileFragment -> {
                    navController.popBackStack(R.id.purchaseFragment, false)
                    navController.navigate(R.id.profileFragment)
                }
            }
            true
        }
        binding.bottomNavView.setOnItemReselectedListener { }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.purchaseFragment -> {
                    binding.warrantyCheck.isClickable = true
                    binding.title.text = "Buy Now"
                    binding.bottomNavView.menu.getItem(0).isChecked = true
                    binding.Root.setBackgroundColor(resources.getColor(R.color.appBG))
                    println("purchase fragment")
                }
                R.id.profileFragment -> {
                    binding.warrantyCheck.isClickable = true
                    binding.bottomNavView.menu.getItem(2).isChecked = true
                    binding.title.text = "Profile"
                    binding.Root.setBackgroundColor(resources.getColor(R.color.appBG))
                    println("profile fragment")
                }
                R.id.warrantyScannerFragment -> {
                    binding.bottomNavView.menu.getItem(1).isChecked = true
                    binding.Root.setBackgroundColor(resources.getColor(R.color.MattPinkDarkBottom))
                }
            }
        }
    }
}