package com.flip.warranty.customer.uitility

import com.flip.warranty.customer.dataModel.ProductDetailsData

interface BuyNowClickInterface {
    fun onClick(pos: Int, data: ProductDetailsData)
}