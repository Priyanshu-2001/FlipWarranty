package com.flip.warranty.utility

object Globals {
    private const val LocalServerIpAddress = "192.168.1.157"
    const val EndPoint = "http://$LocalServerIpAddress:8082"


    const val SignUP = "/signup" // yet to be implemented
    const val Login = "/login"
    const val CreateNewItem = "/createNewItem"
    const val GetProductBySerialNumber = "/getProduct/"
    const val GetSerialNumberList = "/getSerialNumberList"
    const val SignWarrantyForProduct = "/signWarranty"
    const val SellProduct = "/sellProduct"
    const val GetProductWarranty = "/getWarranty/"
    const val GetProductWarrantyStatus = "/getWarrantyStatus/"
    const val GetSoldStatus = "/getSoldStaus/"
    const val GetOwnedProductList = "/getOwnedItemsByUser"

    const val TAG = "Here is your Error --> "
}