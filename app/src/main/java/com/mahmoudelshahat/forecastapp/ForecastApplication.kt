package com.mahmoudelshahat.forecastapp

import android.app.Application
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mahmoudelshahat.forecastapp.data.dp.ForecastDatabase
import com.mahmoudelshahat.forecastapp.data.network.api.*
import com.mahmoudelshahat.forecastapp.data.repository.ForecastRepository
import com.mahmoudelshahat.forecastapp.data.repository.ForecastRepositoryImpl
import com.mahmoudelshahat.forecastapp.provider.LocationProviderImpl
import com.mahmoudelshahat.forecastapp.ui.current_weather.CurrentWeatherModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiService(instance()) }

        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }

        bind() from provider {LocationServices.getFusedLocationProviderClient(instance<Context>())}
        bind<com.mahmoudelshahat.forecastapp.provider.LocationProvider>() with singleton { LocationProviderImpl(instance(),instance()) }





        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance(),instance(),instance()) }

        bind() from provider { CurrentWeatherModelFactory(instance()) }
      }


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}