package com.zpi.zpiapp.physicians


import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.zpi.zpiapp.R


class PhysiciansActivity : AppCompatActivity() {
    lateinit var physiciansFragment : PhysiciansFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_physicians)
        loadFragment()
    }

    private fun loadFragment(){
        if(::physiciansFragment.isInitialized.not()){
            physiciansFragment = PhysiciansFragment()
            PhysiciansPresenter(physiciansFragment)

        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.physicians_activity_frame,physiciansFragment)
                .commit()

    }


}
