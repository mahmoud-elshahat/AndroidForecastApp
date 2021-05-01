package com.mahmoudelshahat.forecastapp.data.network.api

import androidx.lifecycle.LiveData
import com.mahmoudelshahat.forecastapp.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather:LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location:String
    )
}