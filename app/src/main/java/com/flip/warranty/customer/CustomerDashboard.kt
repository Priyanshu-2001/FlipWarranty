package com.flip.warranty.customer

import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
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
        binding.bottomNavView.background = null
        binding.bottomNavView.menu.getItem(1).isEnabled = false
        val animation = AnimationUtils.loadAnimation(this, R.anim.circle_explotion_anim).apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
        }
        binding.addBtn.setOnClickListener {
            binding.addBtn.isVisible = false
            binding.circleCover.isVisible = false
            binding.circleCover.startAnimation(animation) {
                //display fragment when animation is finished

//                binding.Root.setBackgroundColor(ContextCompat.getColor(this, R.color.lightBrown))
                binding.circleCover.isVisible = false
            }

        }
    }
}