package com.mdocarmo.climetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mdocarmo.climetracker.views.PrincipalFragment
import kotlinx.android.synthetic.main.activity_mother.*

class MotherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mother)
        val fragment = PrincipalFragment.newInstance("","")
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mother_container, fragment)
            commit()
        }
    }
}