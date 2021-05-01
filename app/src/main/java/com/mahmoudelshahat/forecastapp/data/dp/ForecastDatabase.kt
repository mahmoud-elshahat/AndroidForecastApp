package com.mahmoudelshahat.forecastapp.data.dp

import android.content.Context
import androidx.room.*
import com.mahmoudelshahat.forecastapp.data.dp.entity.CurrentWeather
import com.mahmoudelshahat.forecastapp.data.dp.entity.WeatherDescriptionTypeConverter
import com.mahmoudelshahat.forecastapp.data.dp.entity.WeatherLocation
import com.mahmoudelshahat.forecastapp.data.dp.unitlocalized.CurrentWeatherDao
import com.mahmoudelshahat.forecastapp.data.dp.unitlocalized.WeatherLocationDao


@Database(
    entities = [CurrentWeather::class,WeatherLocation::class],
    version = 1
)
@TypeConverters(WeatherDescriptionTypeConverter::class)
abstract class ForecastDatabase:RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao


    companion object{
        @Volatile private var instance:ForecastDatabase?=null
        private val LOCK=Any()

        operator fun invoke(context: Context)= instance?: synchronized(LOCK){
            instance?: buildDatabase(context).also { instance=it }
        }

        private fun buildDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext
                ,ForecastDatabase::class.java
                , "forecast.db")
                .build()
    }
}