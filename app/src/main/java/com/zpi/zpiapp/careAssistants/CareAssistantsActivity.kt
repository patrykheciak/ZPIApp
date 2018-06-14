package com.zpi.zpiapp.careAssistants

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.zpi.zpiapp.R
import java.util.*


class CareAssistantsActivity : AppCompatActivity() {
    lateinit var mCareAssistantsFragment : CareAssistantsFragment
    private lateinit var mPresenter: CareAssistantsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_assistants)
        loadFragment()
    }

    private fun loadFragment(){
        if(::mCareAssistantsFragment.isInitialized.not()){
            mCareAssistantsFragment = CareAssistantsFragment()
            mPresenter = CareAssistantsPresenter(mCareAssistantsFragment)

        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.care_assistants_activity_frame,mCareAssistantsFragment)
                .commit()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onViewDestroyed()
    }


}
