package com.mahmoudelshahat.forecastapp.data.dp.unitlocalized

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoudelshahat.forecastapp.data.dp.entity.CURRENT_WEATHER_ID
import com.mahmoudelshahat.forecastapp.data.dp.entity.CurrentWeather
import com.mahmoudelshahat.forecastapp.data.dp.entity.WEATHER_LOCATION_ID
import com.mahmoudelshahat.forecastapp.data.dp.entity.WeatherLocation


@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(weatherLocation: WeatherLocation)

    @Query("SELECT * FROM weather_location WHERE id= $WEATHER_LOCATION_ID")
    fun getCurrentLocation(): LiveData<WeatherLocation>
}