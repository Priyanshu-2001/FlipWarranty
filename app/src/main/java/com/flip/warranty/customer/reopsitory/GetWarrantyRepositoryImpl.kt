package com.flip.warranty.customer.reopsitory

import android.app.Application
import android.content.SharedPreferences
import com.flip.warranty.customer.APIs.WarrantyDetailsAPI

class GetWarrantyRepositoryImpl(
    private val api: WarrantyDetailsAPI,
    val app: Application,
    private val sharedPref: SharedPreferences
) :
    GetWarrantyRepository {
    override suspend fun getWarrantyRepository(SerialNum: String) =
        api.getWarrantyDetailsAPI(SerialNum, "Bearer " + sharedPref.getString("token", " ")!!)
//        api.getWarrantyDetailsAPI(SerialNum , "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MmRhOTViNWY0MmEyYTZiOWEzODFlZTkiLCJpYXQiOjE2NTg5MTU4NTUsImV4cCI6MTY1OTA4ODY1NX0.nFcCgGJqXax4j6zd9oPCcPArlSAkzKMdiJY-0izJfoo")

}