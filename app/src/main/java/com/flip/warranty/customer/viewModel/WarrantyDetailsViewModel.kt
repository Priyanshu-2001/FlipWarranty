package com.flip.warranty.customer.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flip.warranty.customer.dataModel.WarrantyDetails
import com.flip.warranty.customer.reopsitory.GetWarrantyRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarrantyDetailsViewModel @Inject constructor(
    val repository: GetWarrantyRepositoryImpl
) : ViewModel() {

    val serialNumber = MutableLiveData<String>()
    val warrantyDetails = MutableLiveData<WarrantyDetails>()

    init {
        serialNumber.observeForever {
            viewModelScope.launch {
                Log.e("TAG", "Created what i need: ")
                val res =
                    repository.getWarrantyRepository(serialNumber.value.toString())
                if (res.isSuccessful) {
                    warrantyDetails.value = res.body()
                } else {
                    Log.e("TAG", res.raw().toString())
                    Log.e("TAG", res.message().toString())
                    Log.e("TAG", res.errorBody().toString())
                }
            }
        }
    }
}