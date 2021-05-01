package com.mahmoudelshahat.forecastapp.data.dp.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


const val CURRENT_WEATHER_ID =0

@Entity(tableName = "current_weather")
data class CurrentWeather(
    val temperature: Double,
    @SerializedName("feelslike")
    val feelsLike: Double,
    @SerializedName("is_day")
    val isDay: String,
    @SerializedName("observation_time")
    val observationTime: String,
    @SerializedName("weather_code")
    val weatherCode: Int,

    @SerializedName("weather_descriptions")
    val weatherDescription: List<String>,

    @SerializedName("wind_dir")
     val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Double
){

    @PrimaryKey(autoGenerate = false)
    var id:Int = CURRENT_WEATHER_ID
}


class WeatherDescriptionTypeConverter{
    @TypeConverter
    fun fromList(icons: List<String?>?): String? {
        if (icons == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.getType()
        return gson.toJson(icons, type)
    }

    @TypeConverter
    fun fromString(icons: String?): List<String>? {
        if (icons == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.getType()
        return gson.fromJson<List<String>>(icons, type)
    }

}