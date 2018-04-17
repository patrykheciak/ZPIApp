package com.zpi.zpiapp.careAssistants

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.zpi.zpiapp.R


class CareAssistantsActivity : AppCompatActivity() {
    lateinit var careAssistantsFragment : CareAssistantsFragment
    private lateinit var presenter: CareAssistantsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_assistants)
        loadFragment()
    }

    private fun loadFragment(){
        if(::careAssistantsFragment.isInitialized.not()){
            careAssistantsFragment = CareAssistantsFragment()
            presenter = CareAssistantsPresenter(careAssistantsFragment)

        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.care_assistants_activity_frame,careAssistantsFragment)
                .commit()

    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }
}
