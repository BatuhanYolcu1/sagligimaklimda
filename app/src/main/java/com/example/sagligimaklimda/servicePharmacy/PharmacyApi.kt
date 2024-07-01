package com.example.sagligimaklimda.servicePharmacy

import com.example.sagligimaklimda.model.ApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PharmacyApi {

    @GET("pharmacies-on-duty/locations")
    fun getPharmacy(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("apiKey") apiKey: String = "iZ9ViN6e1145QFPjd0DHSdvuvwoyEHYSLBnMEMWprkoXIZuP2Ypdk4z89KkT"
    ): Single<ApiResponse>
}