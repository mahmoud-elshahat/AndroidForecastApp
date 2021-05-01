package com.mahmoudelshahat.forecastapp.ui.current_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudelshahat.forecastapp.data.repository.ForecastRepository

@Suppress("UNCHECKED_CAST")
class CurrentWeatherModelFactory(private  val forecastRepository:ForecastRepository)
    :ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository) as T
    }
}