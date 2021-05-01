package com.mahmoudelshahat.forecastapp.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.mahmoudelshahat.forecastapp.data.dp.entity.WeatherLocation
import com.mahmoudelshahat.forecastapp.internal.LocationPermissionNotGrantedException
import com.mahmoudelshahat.forecastapp.internal.asDeferred
import kotlinx.coroutines.Deferred


const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient, context: Context
) : PreferenceProvider(context), LocationProvider {

    val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastLocation: WeatherLocation): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            false
        }


        val customLocationChanged = hasCustomLocationChanged(lastLocation)

        return deviceLocationChanged || customLocationChanged
    }


    private suspend fun hasDeviceLocationChanged(lastLocation: WeatherLocation): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
            ?: return false

        // Comparing doubles cannot be done with "=="
        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - lastLocation.lat.toDouble()) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastLocation.lon.toDouble()) > comparisonThreshold


    }

    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if (!isUsingDeviceLocation()) {
            val customLocationName = getCustomLocationName()
            return customLocationName != lastWeatherLocation.name
        }
        return false
    }

    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, "Egypt")
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }


    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?> {
        if (hasLocationPermission())
            return fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()

    }


    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    override suspend fun getPreferredLocationString(): String {
        if (isUsingDeviceLocation()) {
            try {
                 val deviceLocation = getLastDeviceLocation().await()
                if(deviceLocation==null || deviceLocation.equals(""))
                    return "${getCustomLocationName()}"
                else
                    return deviceLocation.toString()
            } catch (e: LocationPermissionNotGrantedException) {
                return "${getCustomLocationName()}"
            }
        }
        return "${getCustomLocationName()}"
    }

}