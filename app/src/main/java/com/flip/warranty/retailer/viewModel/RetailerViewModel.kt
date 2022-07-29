package com.flip.warranty.retailer.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.retailer.dataModel.NewProductDataModel
import com.flip.warranty.retailer.repositoryImpl.AddNewProductImpl
import com.flip.warranty.utility.Globals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RetailerViewModel @Inject constructor(
    private val repositoryImpl: AddNewProductImpl,
    val app: Application
) : ViewModel() {

    var response = MutableLiveData<Boolean>()
    val productList = MutableLiveData<ArrayList<ProductDetailsData>>()
    val productListUnsold = MutableLiveData<ArrayList<ProductDetailsData>>()
    val productListSold = MutableLiveData<ArrayList<ProductDetailsData>>()
    val productListUnSigned = MutableLiveData<ArrayList<ProductDetailsData>>()

    fun addProduct(data: NewProductDataModel, token: String) {

        Log.e("TAG", "addProduct: started out")
        viewModelScope.launch {
            Log.e("TAG", "addProduct: started")
            response.value = repositoryImpl.addNewProduct(data, token)
        }
    }

    init {
        productList.observeForever {
            productListUnsold.value = it.filter { prod ->
                prod.soldStatus == "0"
            } as ArrayList
        }
        productList.observeForever {
            productListSold.value = it.filter { prod ->
                prod.soldStatus == "1"
            } as ArrayList
        }
        productList.observeForever {
            productListUnSigned.value = it.filter { prod ->
                prod.signStatus == "1"
            } as ArrayList
        }
        viewModelScope.launch {
            productList.value = repositoryImpl.getProductNumberList()
        }
    }

    fun buyItem(pos: Int) {
        Log.e(Globals.TAG, "buyItem: " + productList.value?.get(pos))

        viewModelScope.launch {
            repositoryImpl.buyProduct(productListUnsold.value!![pos])
        }
        productListUnsold.value!!.removeAt(pos)
        productListUnsold.value = productListUnsold.value
    }
}