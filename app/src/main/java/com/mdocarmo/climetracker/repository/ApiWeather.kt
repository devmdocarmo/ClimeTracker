package com.mdocarmo.climetracker.repository

import com.google.gson.GsonBuilder
import com.mdocarmo.climetracker.models.GETCurrentWeatherData
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiWeather {
/*******************callback=test&id=2172797&units=%2522metric%2522%20or%20%2522imperial%2522&mode=xml%252C%20html&q=London%252Cuk*****************/
/*******************callback : test, id : 123, units : "metric" or "imperial", mode : xml, html, q : London,Cuk*****************************/
    @GET("weather")
    fun getCurrentData(@Query("callback") callback: String = "test", @Query("id")id: Int = 2172797, @Query("units")units: String = "metric", @Query("mode")mode: String = ""):Call<String>
    companion object {
        private const val url = "https://community-open-weather-map.p.rapidapi.com/"
         val instance: ApiWeather by lazy {
             val gson = GsonBuilder()
                 .setLenient()
                 .create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(url)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val request: Request =
                    chain.request().newBuilder()
                        .get()
                        .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "f719ae16c8mshb4fbd664cfe37afp14daedjsn18bc8dc9d2ed").build()
                chain.proceed(request)
            }
            retrofit.client(httpClient.build())
            retrofit.build().create(ApiWeather::class.java)

        }
    }
}