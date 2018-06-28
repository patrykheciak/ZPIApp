package com.zpi.zpiapp.careAssistantCharges

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zpi.zpiapp.R

class CareAssistantChargesActivity: AppCompatActivity() {
    lateinit var mPresenter:CareAssistantChargesContract.CareAssistantChargesPresenter
    lateinit var mFragment: CareAssistantChargesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_assistants)
        loadFragment()
    }

    private fun loadFragment(){
        if( ::mPresenter.isInitialized.not() ){
            mFragment= CareAssistantChargesFragment()
            mPresenter=CareAssistantChargesPresenter( mFragment )
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.care_assistants_activity_frame,mFragment)
                .commit()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onViewDestroyed()
    }

}