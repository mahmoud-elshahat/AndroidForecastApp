package com.mahmoudelshahat.forecastapp.ui.current_weather

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahmoudelshahat.forecastapp.data.dp.entity.CurrentWeather
import com.mahmoudelshahat.forecastapp.data.dp.entity.WeatherLocation
import com.mahmoudelshahat.forecastapp.data.repository.ForecastRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel(), Observable {

    var weather =
        GlobalScope.async(start = CoroutineStart.LAZY) {
            forecastRepository.getCurrentWeather()
        }

    var location =
        GlobalScope.async(start = CoroutineStart.LAZY) {
            forecastRepository.getWeatherLocation()
        }


    @Bindable
    val currentWeather= MutableLiveData<CurrentWeather>()
    @Bindable
    val currentLocation=MutableLiveData<WeatherLocation>()


    @Bindable
    val stillLoading= MutableLiveData<Boolean>()
    init {
        stillLoading.value=true
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }


}