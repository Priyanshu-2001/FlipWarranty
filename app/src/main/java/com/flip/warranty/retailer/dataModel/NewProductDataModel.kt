package com.flip.warranty.retailer.dataModel

data class NewProductDataModel(
    val _manufacturer: String,
    val _purchase_date: String,
    val _retailer: String,
    val display_name: String,
    val image: String,
    val price: String,
    val serial_number: String
)