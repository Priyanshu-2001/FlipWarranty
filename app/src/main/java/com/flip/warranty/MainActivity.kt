package com.flip.warranty

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.flip.warranty.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.scanQRbtn.setOnClickListener {
            scanFunction()
        }
        binding.searchWithSerialNumber.setOnClickListener {
            startWarrantyActivity(binding.serialEditText.text.toString())
        }
    }

    private fun scanFunction() {
        val option = ScanOptions()
        option.setPrompt("Volume up to Turn Flash ON\nVolume down to Turn Flash OFF")
        option.setBeepEnabled(true)
        option.setOrientationLocked(true)
        option.captureActivity = CaptureAct::class.java
        barcodeLauncher.launch(option)
    }

    private fun startWarrantyActivity(sNumber: String) {
        val i = Intent(this, WarrantyInfoActivity::class.java)

        i.putExtra("serialNumber", sNumber)
        startActivity(i)
    }

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG)
                .show()
            startWarrantyActivity(result.contents)
        }
    }
}