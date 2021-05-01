package com.mahmoudelshahat.forecastapp.data.network.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudelshahat.forecastapp.data.network.response.CurrentWeatherResponse
import com.mahmoudelshahat.forecastapp.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val apiService:WeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather= MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val fetchedCurrentWeather=apiService
                .getCurrentWeather(location)
                .await()

            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)

        }catch (e:NoConnectivityException){
            Log.e("Connectivity","No Internet connection")
        }
    }
}