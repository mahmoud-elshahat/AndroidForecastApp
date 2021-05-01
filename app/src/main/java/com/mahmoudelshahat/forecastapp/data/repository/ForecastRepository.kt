package com.mahmoudelshahat.forecastapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudelshahat.forecastapp.data.dp.entity.CurrentWeather
import com.mahmoudelshahat.forecastapp.data.dp.entity.WeatherLocation
import com.mahmoudelshahat.forecastapp.data.network.response.CurrentWeatherResponse

interface ForecastRepository {

    suspend fun getCurrentWeather(): LiveData<CurrentWeather>


    suspend fun getWeatherLocation():LiveData<WeatherLocation>
}