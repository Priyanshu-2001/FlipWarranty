package com.flip.warranty.customer.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.flip.warranty.R
import com.flip.warranty.customer.WarrantyInfoActivity
import com.flip.warranty.customer.uitility.CaptureAct
import com.flip.warranty.databinding.FragmentWarrantyScannerBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class WarrantyScannerFragment : Fragment() {
    lateinit var binding: FragmentWarrantyScannerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_warranty_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWarrantyScannerBinding.bind(view)
        binding.scanQRbtn.background =
            ResourcesCompat.getDrawable(resources, R.drawable.premium_btn, null)
        binding.searchWithSerialNumber.background =
            ResourcesCompat.getDrawable(resources, R.drawable.premium_btn, null)
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

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Scanned: " + result.contents, Toast.LENGTH_LONG)
                .show()
            startWarrantyActivity(result.contents)
        }
    }

    private fun startWarrantyActivity(sNumber: String) {
        val i = Intent(requireContext(), WarrantyInfoActivity::class.java)
        i.putExtra("serialNumber", sNumber)
        startActivity(i)
    }


    companion object {
        @JvmStatic
        fun newInstance() = WarrantyScannerFragment()
    }
}