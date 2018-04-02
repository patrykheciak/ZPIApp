package com.zpi.zpiapp.careAssistants

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.CareAssistant
import kotlinx.android.synthetic.main.care_assistants_row.*
import kotlinx.android.synthetic.main.fragment_care_assistants.*

class CareAssistantsFragment : Fragment(),CareAssistantsContract.View {

    override fun showCareAssistants(careAssistants: List<CareAssistant>) {
        careAssistantsFound.visibility=View.VISIBLE
        careAssistantsNotFound.visibility=View.GONE
        careAssistantsConnectionProblem.visibility=View.GONE

        (careAssistantsRecyclerView.adapter as CareAssistantsAdapter).items = careAssistants
        careAssistantsRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun showCareAssistantsNotFound() {
        careAssistantsFound.visibility=View.GONE
        careAssistantsNotFound.visibility=View.VISIBLE
        careAssistantsConnectionProblem.visibility=View.GONE
    }

    override fun showConnectionError() {
        careAssistantsFound.visibility=View.GONE
        careAssistantsNotFound.visibility=View.GONE
        careAssistantsConnectionProblem.visibility=View.VISIBLE
    }

    override fun showSnackBarError(error: String) {
        Snackbar.make(careAssistantsRoot,error,Snackbar.LENGTH_LONG).show();
    }

    override fun clearAddCareAssistant() {
        careAssistantLoginTV.text=""
    }

    private lateinit var presenter: CareAssistantsContract.Presenter

    override fun setPresenter(presenter: CareAssistantsContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_care_assistants, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        careAssistantsRecyclerView.adapter=CareAssistantsAdapter()
        careAssistantsRecyclerView.layoutManager=LinearLayoutManager(context)

        fabCareAssistants.setOnClickListener {
            hideKeyboard()
            presenter.addNewCareAssistant(careAssistantLoginTV.text as String)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    private fun hideKeyboard() {
        val view = InteractionsFragment@ this.activity.currentFocus
        if (view != null) {
            val imm = InteractionsFragment@ this.activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}
