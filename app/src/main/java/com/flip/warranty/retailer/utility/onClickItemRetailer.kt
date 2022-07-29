package com.flip.warranty.retailer.utility

import com.flip.warranty.customer.dataModel.ProductDetailsData

interface onClickItemRetailer {
    fun onclick(pos: Int, data: ProductDetailsData, type: Int)
}