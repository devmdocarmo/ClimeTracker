package com.mdocarmo.climetracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.mdocarmo.climetracker.views.PrincipalFragment

class MotherActivity : AppCompatActivity() {
    private val CODE_ASK_PERMISSION = 111
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mother)
        if (ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), CODE_ASK_PERMISSION)
            }
        }
        val fragment = PrincipalFragment.newInstance("","")
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mother_container, fragment)
            commit()
        }
    }
}