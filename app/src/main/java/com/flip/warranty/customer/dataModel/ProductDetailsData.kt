package com.flip.warranty.customer.dataModel

data class ProductDetailsData(
    val image: String,
    val manufacturer: String,
    val price: String,
    val prodDisplayName: String,
    val prod_id: String,
    val prod_owner: String,
    val purchase_date: String,
    val retailerName: String
) {
    var soldStatus: String = "0"
    var serialNUmber: String = "0"
}