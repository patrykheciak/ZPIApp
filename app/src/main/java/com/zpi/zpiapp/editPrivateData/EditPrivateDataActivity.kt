package com.zpi.zpiapp.editPrivateData

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zpi.zpiapp.R


class EditPrivateDataActivity : AppCompatActivity(){
    lateinit var mEditPrivateDataFragment:EditPrivateDataFragment
    lateinit var mPresenter:EditPrivateDataContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_data)
        loadFragment()
    }

    private fun loadFragment(){
        if (::mEditPrivateDataFragment.isInitialized.not()){
            mEditPrivateDataFragment= EditPrivateDataFragment()
            mPresenter = EditPrivateDataPresenter(mEditPrivateDataFragment)
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.private_data_activity_frame,mEditPrivateDataFragment)
                .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onViewDestroyed()
    }
}