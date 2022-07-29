package com.flip.warranty.retailer.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flip.warranty.customer.dataModel.ProductDetailsData
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
    private val productList = MutableLiveData<ArrayList<ProductDetailsData>>()
    val productListUnsold = MutableLiveData<ArrayList<ProductDetailsData>>()
    val productListSold = MutableLiveData<ArrayList<ProductDetailsData>>()
    val productListUnSigned = MutableLiveData<ArrayList<ProductDetailsData>>()

    fun addProduct(data: NewProductDataModel, token: String) {

        viewModelScope.launch {
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

    fun clickOnlisted(pos: Int, data: ProductDetailsData) {

    }

    fun clickOnUnsigned(pos: Int, data: ProductDetailsData) {
        val rem = productListUnSigned.value!!.removeAt(pos)
        productListSold.value!!.add(rem)
        productListUnSigned.value = productListUnSigned.value
        productListSold.value = productListSold.value
        viewModelScope.launch {
            repositoryImpl.signTheUnsignedProduct(data)
        }
    }

    fun clickOnSigned(pos: Int, data: ProductDetailsData) {

    }
}