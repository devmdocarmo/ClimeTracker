package com.mdocarmo.climetracker.provider

import android.util.Log
import com.mdocarmo.climetracker.repository.ApiWeather
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback

class ProviderWeather {

    fun getCurrentData() {
        ApiWeather.instance.getCurrentData().enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    EventBus.getDefault().post(t.message.toString())
                }

                override fun onResponse(
                    call: Call<String>,
                    response: retrofit2.Response<String>
                ) {
                    response.body()?.substringAfter("(")?.let {
                        it.substringBeforeLast(")")
                        EventBus.getDefault().post(it);
                    }
                }
            }
        )
    }

}