package com.flip.warranty.customer.dataModel

data class Receipt(
    val blockHash: String,
    val blockNumber: Int,
    val contractAddress: Any,
    val cumulativeGasUsed: Int,
    val effectiveGasPrice: String,
    val from: String,
    val gasUsed: Int,
    val logs: List<Any>,
    val logsBloom: String,
    val rawLogs: List<Any>,
    val status: Boolean,
    val to: String,
    val transactionHash: String,
    val transactionIndex: Int,
    val type: String
)