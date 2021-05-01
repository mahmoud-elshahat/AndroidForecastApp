package com.mahmoudelshahat.forecastapp.data.dp.entity


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "weather_location")
data class WeatherLocation(
    val country: String,
    val lat: String,
    val lon:String,
    val localtime: String,
    val name: String,
    val region: String,
    @SerializedName("timezone_id")
    val timezoneId: String,
    @SerializedName("utc_offset")
    val utcOffset: String,
    @SerializedName("localtime_epoch")
    val localTimeEpoch: Long
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID




    val zonedDateTime: ZonedDateTime
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            val instant = Instant.ofEpochSecond(localTimeEpoch)
            val zonedId = ZoneId.of(timezoneId)
            return ZonedDateTime.ofInstant(instant, zonedId)

        }
}