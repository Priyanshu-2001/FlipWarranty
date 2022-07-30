package com.flip.warranty.customer.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.customer.dataModel.WarrantyDetails
import com.flip.warranty.customer.reopsitory.GetProductRepositoryImpl
import com.flip.warranty.customer.reopsitory.GetWarrantyRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarrantyDetailsViewModel @Inject constructor(
    val repository: GetWarrantyRepositoryImpl,
    val productDetailRepoImpl: GetProductRepositoryImpl,
    val app: Application
) : ViewModel() {

    val serialNumber = MutableLiveData<String>()
    val warrantyDetails = MutableLiveData<WarrantyDetails>()
    val productDetails = MutableLiveData<ProductDetailsData>()

    init {
        serialNumber.observeForever {
            viewModelScope.launch {
                Log.e("TAG", "Created what i need: ")
                val res =
                    repository.getWarrantyRepository(serialNumber.value.toString())
                if (res.isSuccessful) {
                    warrantyDetails.value = res.body()
                } else {
                    Toast.makeText(
                        app.applicationContext,
                        "Product Not Registered Yet",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("TAG", res.raw().toString())
                    Log.e("TAG", res.message().toString())
                    Log.e("TAG", res.errorBody().toString())
                }

                productDetails.value = productDetailRepoImpl.getSingleProductDetail(it)

            }
        }
    }
}