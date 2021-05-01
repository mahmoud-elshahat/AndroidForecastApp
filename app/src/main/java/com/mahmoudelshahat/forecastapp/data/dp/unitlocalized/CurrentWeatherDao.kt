package com.mahmoudelshahat.forecastapp.data.dp.unitlocalized

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoudelshahat.forecastapp.data.dp.entity.CurrentWeather

import com.mahmoudelshahat.forecastapp.data.dp.entity.CURRENT_WEATHER_ID
import com.mahmoudelshahat.forecastapp.data.network.response.CurrentWeatherResponse

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(currentWeather: CurrentWeather)

    @Query("SELECT * FROM current_weather WHERE id= $CURRENT_WEATHER_ID")
    fun getCurrentWeather():LiveData<CurrentWeather>
}