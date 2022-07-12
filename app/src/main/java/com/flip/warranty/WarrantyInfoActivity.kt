package com.flip.warranty

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.flip.warranty.databinding.ActivityWarrantyInfoBinding

class WarrantyInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityWarrantyInfoBinding

    //    val colorList = listOf("#FF7582" , "#4FE0B6" , "#CC184E", "#FFE3B3" , "#5983FC" , "#EA80FC" , "#92DE8B" , "#88F4FF" , "#C1B9AE" , "#A5CAD2" , "#BA5082")
//    val colorList = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_warranty_info)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        val colorList = resources.getIntArray(R.array.BgColorList)
//        val colour = colorList.random()
        Log.e("TAG", "onCreate size : ${colorList[1]}")
        binding.warrantyCard.cardView.setBackgroundColor(colorList[colorList.indices.random()])
    }
}