package com.flip.warranty.customer.reopsitory

import com.flip.warranty.customer.dataModel.WarrantyDetails
import retrofit2.Response

interface GetWarrantyRepository {
    suspend fun getWarrantyRepository(SerialNum: String): Response<WarrantyDetails>
}