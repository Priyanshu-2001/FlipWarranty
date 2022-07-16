package com.flip.warranty.retailer.ui.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.flip.warranty.R
import com.flip.warranty.databinding.AddNewProductBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNewProductForm : BottomSheetDialogFragment() {

    lateinit var binding: AddNewProductBottomsheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogThemeNoFloating)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_new_product_bottomsheet, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AddNewProductBottomsheetBinding.bind(view)

        binding.addImage.setOnClickListener {
            imagePicLauncher.launch("image/*")
        }
        binding.deleteBtn.setOnClickListener {
            binding.imageView.visibility = View.GONE
            binding.addImage.visibility = View.VISIBLE
            binding.deleteBtn.visibility = View.GONE
        }

    }

    private val imagePicLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.imageView.visibility = View.VISIBLE
        binding.addImage.visibility = View.INVISIBLE
        binding.deleteBtn.visibility = View.VISIBLE
        binding.imageView.setImageURI(it)
    }
}