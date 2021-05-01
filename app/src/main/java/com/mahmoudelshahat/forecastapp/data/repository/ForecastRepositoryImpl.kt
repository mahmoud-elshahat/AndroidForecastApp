package com.mahmoudelshahat.forecastapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.mahmoudelshahat.forecastapp.data.dp.unitlocalized.CurrentWeatherDao
import com.mahmoudelshahat.forecastapp.data.dp.unitlocalized.WeatherLocationDao
import com.mahmoudelshahat.forecastapp.data.dp.entity.CurrentWeather
import com.mahmoudelshahat.forecastapp.data.dp.entity.WeatherLocation
import com.mahmoudelshahat.forecastapp.data.network.api.WeatherNetworkDataSource
import com.mahmoudelshahat.forecastapp.data.network.response.CurrentWeatherResponse
import com.mahmoudelshahat.forecastapp.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val locationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCrruentWeather ->
            persistFetchedCurrentWeather(newCrruentWeather)
        }
    }


    override suspend fun getCurrentWeather(): LiveData<CurrentWeather> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getCurrentWeather()
        }
    }


    private fun persistFetchedCurrentWeather(fetchedWeatherResponse: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.insertOrUpdate(fetchedWeatherResponse.currentWeather)
            locationDao.insertOrUpdate((fetchedWeatherResponse.location))
        }
    }


    private suspend fun initWeatherData() {
        val weatherLocation=locationDao.getCurrentLocation().value

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            if(weatherLocation == null || locationProvider.hasLocationChanged(weatherLocation))
            {
                fetchCurrentWeather()
                return
            }
            isFetchNeeded(weatherLocation.zonedDateTime)
            fetchCurrentWeather()
        }else
        fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {

        weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString())
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }







    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return  withContext(Dispatchers.IO){
            return@withContext locationDao.getCurrentLocation()
        }
    }

}