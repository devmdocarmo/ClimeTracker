package com.mdocarmo.climetracker.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import com.mdocarmo.climetracker.R
import com.mdocarmo.climetracker.models.GETCurrentWeatherData
import com.mdocarmo.climetracker.provider.ProviderWeather
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.json.JSONObject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PrincipalFragment.newInstance] factory method to
 * create an instance of this fragment.
 * @author: Miguel Do Carmo
 */
class PrincipalFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val provider = ProviderWeather()
    private var responseCurrentData: GETCurrentWeatherData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_principal, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PrincipalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        provider.getCurrentData()
    }

    @Subscribe
    fun onEvent(event: String) {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val aux = JSONObject(event)
        responseCurrentData = gson.fromJson(aux.toString(), GETCurrentWeatherData::class.java)
        Toast.makeText(this.context, responseCurrentData.toString(), Toast.LENGTH_LONG).show()
    }
}