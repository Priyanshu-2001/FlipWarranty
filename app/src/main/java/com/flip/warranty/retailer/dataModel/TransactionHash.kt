package com.flip.warranty.retailer.dataModel

data class TransactionHash(
    val logs: List<Any>,
    val receipt: Receipt,
    val tx: String
)