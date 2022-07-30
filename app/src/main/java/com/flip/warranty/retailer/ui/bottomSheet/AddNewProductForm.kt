package com.flip.warranty.retailer.ui.bottomSheet

import android.content.SharedPreferences
import android.net.Uri
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
import com.flip.warranty.utility.Globals.TAG
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNewProductForm : BottomSheetDialogFragment() {
    lateinit var binding: AddNewProductBottomsheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogThemeNoFloating)
    }

    @Inject
    lateinit var securePreferences: SharedPreferences

    var imageUrl: Uri? = null
    lateinit var downloadUrl: UploadTask.TaskSnapshot
    val viewModel: RetailerViewModel by viewModels()

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
                val database =
                    FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app")
                val key = database.getReference("temp").push().key

                if (imageUrl != null) {
                    checkAndUploadImage(key!!)
                } else {
                    Toast.makeText(
                        context,
                        "There is no Imagee Uploaded By You",
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.e(TAG, "onCreateView: $key")
                    viewModel.addProduct(
                        NewProductDataModel(
                            binding.description.text.toString(),
                            (System.currentTimeMillis() / 1000L).toString(),
                            securePreferences.getString("email", "null")!!,
                            binding.name.text.toString(),
                            "https://picsum.photos/100/100", //random image for now
                            binding.price.text.toString(),
                            key!!
                        ),
                        securePreferences.getString("token", "")!!
                    )
                }
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

    private fun checkAndUploadImage(key: String) {
        val file = imageUrl
        val storageRef =
            FirebaseStorage.getInstance().reference.child("images/${System.currentTimeMillis()}")
        file?.let { storageRef.putFile(it) }?.addOnSuccessListener { taskSnapshot ->
            storageRef.downloadUrl.addOnSuccessListener {
                viewModel.addProduct(
                    NewProductDataModel(
                        binding.description.text.toString(),
                        (System.currentTimeMillis() / 1000L).toString(),
                        securePreferences.getString("email", "null")!!,
                        binding.name.text.toString(),
                        it.toString(), //random image for now
                        binding.price.text.toString(),
                        key
                    ),
                    securePreferences.getString("token", "")!!
                )
            }
        }


    }

    private val imagePicLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            binding.imageView.visibility = View.VISIBLE
            binding.addImage.visibility = View.INVISIBLE
            binding.deleteBtn.visibility = View.VISIBLE
            println("uir $it")
            imageUrl = it
            binding.imageView.setImageURI(it)
        }

    }
}