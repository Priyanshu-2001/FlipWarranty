package com.flip.warranty.customer.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.customer.reopsitory.GetProductRepositoryImpl
import com.flip.warranty.utility.Globals.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyNowViewModel @Inject constructor(val repository: GetProductRepositoryImpl) : ViewModel() {
    val productList = MutableLiveData<ArrayList<ProductDetailsData>>()

    init {
        viewModelScope.launch {
            productList.value = repository.getProductNumberList()
        }
    }

    fun buyItem(pos: Int) {
        Log.e(TAG, "buyItem: " + productList.value?.get(pos))

        val list = productList.value
        viewModelScope.launch {
            repository.buyProduct(list!![pos])
        }
        list?.removeAt(pos)
        productList.value = list!!

    }

}