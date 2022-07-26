package com.flip.warranty.retailer.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flip.warranty.retailer.dataModel.NewProductDataModel
import com.flip.warranty.retailer.repositoryImpl.AddNewProductImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RetailerViewModel @Inject constructor(
    private val repositoryImpl: AddNewProductImpl,
    val app: Application
) : ViewModel() {

    var response = MutableLiveData<Boolean>()
    fun addProduct(data: NewProductDataModel, token: String) {

        Log.e("TAG", "addProduct: started out")
        viewModelScope.launch {
            Log.e("TAG", "addProduct: started")
            response.value = repositoryImpl.addNewProduct(data, token)
        }
    }

}