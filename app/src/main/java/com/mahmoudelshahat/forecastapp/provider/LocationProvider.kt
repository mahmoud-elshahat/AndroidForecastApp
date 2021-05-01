package com.mahmoudelshahat.forecastapp.provider

import com.mahmoudelshahat.forecastapp.data.dp.entity.WeatherLocation

interface LocationProvider {

    suspend fun hasLocationChanged(lastLocation:WeatherLocation):Boolean

    suspend fun getPreferredLocationString():String
}