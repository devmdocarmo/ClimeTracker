package com.mdocarmo.climetracker.provider

import android.util.Log
import com.mdocarmo.climetracker.repository.ApiWeather
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback

class ProviderWeather {

    fun getCurrentData(param : String) {
        ApiWeather.instance.getCurrentData(q = param).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    EventBus.getDefault().post(t.message.toString())
                }

                override fun onResponse(
                    call: Call<String>,
                    response: retrofit2.Response<String>
                ) {
                    Log.d("response", response.body().toString())
                        EventBus.getDefault().post(response.body())
                }
            }
        )
    }

}