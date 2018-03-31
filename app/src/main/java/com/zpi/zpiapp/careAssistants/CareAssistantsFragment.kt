package com.zpi.zpiapp.careAssistants

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.CareAssistant

class CareAssistantsFragment : Fragment(),CareAssistantsContract.View {
    private lateinit var presenter: CareAssistantsContract.Presenter

    override fun setPresenter(presenter: CareAssistantsContract.Presenter) {
        this.presenter = presenter
    }

    override fun showCareAssistants(careAssistants: List<CareAssistant>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNoCareAssistants() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadCareAssistants() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchCareAssistants() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addNewCareAssistant() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showConnectionError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_care_assistants, container, false)
    }

}
