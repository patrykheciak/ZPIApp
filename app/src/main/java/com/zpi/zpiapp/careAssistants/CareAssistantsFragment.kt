package com.zpi.zpiapp.careAssistants

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.CareAssistant
import kotlinx.android.synthetic.main.fragment_care_assistants.*

class CareAssistantsFragment : Fragment(),CareAssistantsContract.View {
    override fun showCareAssistantsNotFound() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showConnectionError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRESTMessageError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAddCareAssistant() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var presenter: CareAssistantsContract.Presenter

    override fun setPresenter(presenter: CareAssistantsContract.Presenter) {
        this.presenter = presenter
    }

    override fun showCareAssistants(careAssistants: List<CareAssistant>) {
/*        if (careAssistants.isEmpty()) {
            careAssistantsNotFound.visibility = View.GONE
            careAssistantsFound.visibility = View.VISIBLE
        } else {
            (careAssistantsRecyclerView.adapter as CareAssistantsAdapter).items = careAssistants
            careAssistantsRecyclerView.adapter.notifyDataSetChanged()
        }*/
    }






    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_care_assistants, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*        careAssistantsRecyclerView.adapter=CareAssistantsAdapter()
        careAssistantsRecyclerView.layoutManager=LinearLayoutManager(context)*/
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

}
