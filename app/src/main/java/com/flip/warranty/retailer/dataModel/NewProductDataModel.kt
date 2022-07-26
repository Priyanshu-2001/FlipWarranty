package com.flip.warranty.retailer.dataModel

import com.google.gson.annotations.SerializedName

data class NewProductDataModel(
    @SerializedName("_manufacturer")
    val product_details: String,
    val _purchase_date: String,
    val _retailer: String,
    val display_name: String,
    val image: String,
    val price: String,
    val serial_number: String
)