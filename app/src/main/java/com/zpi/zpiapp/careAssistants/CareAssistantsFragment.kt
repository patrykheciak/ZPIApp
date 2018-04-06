package com.zpi.zpiapp.careAssistants

import android.app.AlertDialog
import android.content.Context
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


    private lateinit var mPresenter: CareAssistantsContract.Presenter

    override fun setPresenter(presenter: CareAssistantsContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun showCareAssistants(careAssistants: List<CareAssistant>) {
        care_assistants_found_frame.visibility=View.VISIBLE
        care_assistants_not_found_frame.visibility=View.GONE
        care_assistants_connection_problem_frame.visibility=View.GONE

        (care_assistants_found_recycler_view.adapter as CareAssistantsAdapter).items = careAssistants
        care_assistants_found_recycler_view.adapter.notifyDataSetChanged()
    }

    override fun showCareAssistantsNotFound() {
        care_assistants_found_frame.visibility=View.GONE
        care_assistants_not_found_frame.visibility=View.VISIBLE
        care_assistants_connection_problem_frame.visibility=View.GONE
    }

    override fun showConnectionError() {
        care_assistants_found_frame.visibility=View.GONE
        care_assistants_not_found_frame.visibility=View.GONE
        care_assistants_connection_problem_frame.visibility=View.VISIBLE
    }

    override fun showSnackBarError(error: String) {
        Snackbar.make(care_assistants_root,error,Snackbar.LENGTH_LONG).show();
    }

    override fun clearAddCareAssistant() {
        care_assistant_row_surname.text=""
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_care_assistants, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        care_assistants_found_recycler_view.adapter=CareAssistantsAdapter(object :CareAssistantsAdapter.ClickListener{
            override fun onRemoveButtonClicked(careAssistantId: Int) {
                mPresenter.checkRemovingCareAssistatn(careAssistantId)
            }
        })
        care_assistants_found_recycler_view.layoutManager=LinearLayoutManager(context)

        care_assistants_add_panel_fab.setOnClickListener {
            hideKeyboard()
            mPresenter.addNewCareAssistant(care_assistants_add_panel_login.text.toString() )
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    private fun hideKeyboard() {
        val view = InteractionsFragment@ this.activity.currentFocus
        if (view != null) {
            val imm = InteractionsFragment@ this.activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun showRemoveDialog(careAssistant: CareAssistant) {
        val builder = AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert)

        builder.setTitle("Usuwanie")
                .setMessage("Czy na pewno chcesz usunąć ${careAssistant.name} ${careAssistant.surname}")
                .setPositiveButton(android.R.string.yes, { _, _ ->
                    mPresenter.removeCareAssistant(careAssistant.idCareAssistant)
                })
                .setNegativeButton(android.R.string.no, { _, _ ->
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
    }

}
