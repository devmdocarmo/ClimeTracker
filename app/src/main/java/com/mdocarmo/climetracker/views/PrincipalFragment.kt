package com.mdocarmo.climetracker.views

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import com.mdocarmo.climetracker.R
import com.mdocarmo.climetracker.models.GETCurrentWeatherData
import com.mdocarmo.climetracker.provider.ProviderWeather
import kotlinx.android.synthetic.main.fragment_principal.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.json.JSONObject
import java.io.IOException
import java.util.*


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
    private var ubicationLocal: String?= null
    private val provider = ProviderWeather()
    private var responseCurrentData: GETCurrentWeatherData? = null
    private var locationManager : LocationManager? = null
    private var shortAnimationDuration: Int = 1000
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

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
        // La App esta en ejecución
        getUbication()
        ubicationLocal?.let { provider.getCurrentData(it) }



    }

    @Subscribe
    fun onEvent(event: String) {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val aux = JSONObject(event)
        responseCurrentData = gson.fromJson(aux.toString(), GETCurrentWeatherData::class.java)
        image_climate.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.

            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .interpolator = AccelerateInterpolator()
        }
        temperature_actual.apply {
            setAnimation(this)
            text = responseCurrentData?.main?.temp.toString().substringBeforeLast('.')+"°"
        }
        city_name.apply {
            setAnimation(this)
            text = responseCurrentData?.name+","
        }
        country_name.apply {
            setAnimation(this)
            text = responseCurrentData?.sys?.country
        }
    }

    fun setAnimation(view: View){
        view.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.

            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .interpolator = AccelerateInterpolator()
        }
    }
    /****************getter of the ubication ****************************/
    fun getUbication(){
        locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager?
        val location = if (ActivityCompat.checkSelfPermission(
                this@PrincipalFragment.context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@PrincipalFragment.context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }else {
            locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }
        if (location!=null)
            thisLocation(location.latitude,location.longitude)
    }

    fun thisLocation(lat: Double, lon: Double){
        val geocoder = Geocoder(this@PrincipalFragment.context, Locale.getDefault())
        val addresses = ArrayList<Address>()
        try {
            addresses.addAll(geocoder.getFromLocation(lat,lon,10))
            if (addresses.size>0){
                for(value in addresses){
                    if (value.locality != null && value.locality.isNotEmpty()){
                        ubicationLocal = value.locality +", "+ value.countryCode.toLowerCase()
                    }
                }
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
    }

    fun setAlarm(hour: Int, min: Int){
        alarmMgr = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, PrincipalFragment::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        // Set the alarm to start at 8:30 a.m.
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            1000 * 60 * 20,
            alarmIntent
        )
    }
}