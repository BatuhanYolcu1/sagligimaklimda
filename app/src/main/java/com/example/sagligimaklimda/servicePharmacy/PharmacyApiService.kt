package com.example.sagligimaklimda.servicePharmacy

import com.example.sagligimaklimda.model.ApiResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PharmacyApiService {

    private val BASE_URL="https://www.nosyapi.com/apiv2/service/"

    val api = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(PharmacyApi::class.java)

    fun getData(lat:Double,long:Double,key:String): Single<ApiResponse> {
        return api.getPharmacy(lat,long,key)
    }
}