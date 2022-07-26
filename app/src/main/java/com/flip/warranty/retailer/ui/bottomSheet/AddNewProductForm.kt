package com.flip.warranty.retailer.ui.bottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.flip.warranty.R
import com.flip.warranty.databinding.AddNewProductBottomsheetBinding
import com.flip.warranty.retailer.dataModel.NewProductDataModel
import com.flip.warranty.retailer.utility.Validation
import com.flip.warranty.retailer.viewModel.RetailerViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_new_product_bottomsheet,
            container,
            false
        )

        val viewModel: RetailerViewModel by viewModels()

        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        val securePreferences = EncryptedSharedPreferences.create(
            "Main_File",
            mainKeyAlias,
            requireContext(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        binding.addImage.setOnClickListener {
            imagePicLauncher.launch("image/*")
        }
        binding.deleteBtn.setOnClickListener {
            binding.imageView.visibility = View.GONE
            binding.addImage.visibility = View.VISIBLE
            binding.deleteBtn.visibility = View.GONE
        }
        binding.addProductBtn.setOnClickListener {
            Log.e("TAG", "onCreateView: clicked")
            if (Validation.validateName(binding.name) &&
                Validation.validateDescription(binding.description) &&
                Validation.validatePrice(binding.price)
            ) {
                viewModel.addProduct(
                    NewProductDataModel(
                        binding.description.text.toString(),
                        (System.currentTimeMillis() / 1000L).toString(),
                        securePreferences.getString("email", "null")!!,
                        binding.name.text.toString(),
                        "ImageURL",
                        binding.price.text.toString(),
                        "Serial Nimber from Firebse"
                    ),
                    securePreferences.getString("token", "")!!
                )
            }
        }
        viewModel.response.observe(this) {
            if (it) {
                Toast.makeText(context, "Product Added Successfully", Toast.LENGTH_LONG).show()
                dismiss()
            } else {
                Toast.makeText(context, "Failed To Add Product", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    private val imagePicLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            binding.imageView.visibility = View.VISIBLE
            binding.addImage.visibility = View.INVISIBLE
            binding.deleteBtn.visibility = View.VISIBLE
            println("uir $it")
            binding.imageView.setImageURI(it)
        }

    }
}