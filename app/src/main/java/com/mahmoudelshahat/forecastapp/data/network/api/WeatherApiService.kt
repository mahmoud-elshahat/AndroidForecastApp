package com.mahmoudelshahat.forecastapp.data.network.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mahmoudelshahat.forecastapp.data.network.api.ConnectivityInterceptor
import com.mahmoudelshahat.forecastapp.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "85020135a5353200fe4d7e9cc880fa8d"
//http://api.weatherstack.com/current?access_key=85020135a5353200fe4d7e9cc880fa8d&query=cairo

interface WeatherApiService {

    @GET("current")
    fun getCurrentWeather(
        @Query("query")location:String
    ):Deferred<CurrentWeatherResponse>


    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ):WeatherApiService{
            val requestInterceptor=Interceptor{chain ->

                val url=chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()

                val request=chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherstack.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }

}