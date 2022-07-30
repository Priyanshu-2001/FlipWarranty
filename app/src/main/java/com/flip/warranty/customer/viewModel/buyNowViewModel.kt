package com.flip.warranty.customer.viewModel

import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flip.warranty.R
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.customer.reopsitory.GetProductRepositoryImpl
import com.flip.warranty.utility.Globals.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyNowViewModel @Inject constructor(val repository: GetProductRepositoryImpl) : ViewModel() {
    private val productList = MutableLiveData<ArrayList<ProductDetailsData>>()
    val productListUnsold = MutableLiveData<ArrayList<ProductDetailsData>>()
    val productHistoryList = MutableLiveData<ArrayList<ProductDetailsData>>()

    init {
        productList.observeForever {
            productListUnsold.value = it.filter { prod ->
                prod.soldStatus == "0"
            } as ArrayList
        }
        loadDataBuyData()
        loadOrderHistory()
    }

    private fun loadOrderHistory() {
        viewModelScope.launch {
            productHistoryList.value = repository.getOrderHistoryProductList()
        }
    }

    fun loadDataBuyData() {
        viewModelScope.launch {
            productList.value = repository.getProductNumberList()
        }
    }

    fun buyItem(pos: Int, progressBar: ProgressBar) {
        Log.e(TAG, "buyItem: " + productList.value?.get(pos))

        viewModelScope.launch {
            val res = repository.buyProduct(productListUnsold.value!![pos])
            res.observeForever { response ->
                if (response.isSuccessful) {
                    AlertDialog.Builder(progressBar.context, R.style.MyDialogTheme)
                        .setIcon(R.drawable.ic_baseline_done_24)
                        .setMessage(
                            StringBuilder("Your Transaction is Completed with Txn Hash ID - ").append(
                                response.body()?.transactionHash?.tx
                            )
                        ).setCancelable(true)
                        .create().show()
                    productListUnsold.value!!.removeAt(pos)
                    productListUnsold.value = productListUnsold.value
                } else {
                    AlertDialog.Builder(progressBar.context, R.style.MyDialogTheme)
                        .setIcon(R.drawable.ic_baseline_clear_24)
                        .setMessage(
                            StringBuilder("Your Transaction Failed ")
                        ).setCancelable(true)
                        .create().show()
                }
                progressBar.visibility = View.GONE
            }
        }

    }

}