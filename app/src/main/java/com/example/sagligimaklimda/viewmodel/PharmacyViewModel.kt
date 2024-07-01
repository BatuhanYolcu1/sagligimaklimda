package com.example.sagligimaklimda.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sagligimaklimda.model.ApiResponse
import com.example.sagligimaklimda.model.Pharmacy
import com.example.sagligimaklimda.servicePharmacy.PharmacyApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PharmacyViewModel(application: Application) : AndroidViewModel(application) {


    private val pharmacyApiService = PharmacyApiService()
    private val disposable = CompositeDisposable()


    val pharmacyValue = MutableLiveData<List<Pharmacy>>()







    fun getDataFromApi(latitude:Double,longitude: Double) {


        disposable.add(
            pharmacyApiService.getData(latitude, longitude ,"iZ9ViN6e1145QFPjd0DHSdvuvwoyEHYSLBnMEMWprkoXIZuP2Ypdk4z89KkT")
                .subscribeOn(Schedulers.newThread())//UI thread'ini bloke etmiyoz
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResponse>() {
                    override fun onSuccess(response: ApiResponse) {
                        pharmacyValue.value = response.data
                        Log.d("PharmacyViewModel", "API'dan çekilen veriler: ${response.data.size} adet eczane bulundu.")
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        Log.e("PharmacyViewModel", "API'dan veri çekme hatası: ${e.message}")
                    }
                })
        )

    }

    fun updateLocation(latitude: Double, longitude: Double) {
        getDataFromApi(latitude, longitude)
    }



    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}