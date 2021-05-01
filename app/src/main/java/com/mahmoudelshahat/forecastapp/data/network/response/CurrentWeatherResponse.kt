package com.mahmoudelshahat.forecastapp.data.network.response


import com.google.gson.annotations.SerializedName
import com.mahmoudelshahat.forecastapp.data.dp.entity.CurrentWeather
import com.mahmoudelshahat.forecastapp.data.dp.entity.WeatherLocation

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeather: CurrentWeather,
    val location: WeatherLocation
)