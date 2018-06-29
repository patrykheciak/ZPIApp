package com.zpi.zpiapp.careAssistantCharges

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
import com.zpi.zpiapp.model.PatientDTO
import kotlinx.android.synthetic.main.fragment_care_assistants.*
import kotlinx.android.synthetic.main.fragment_care_assistants_charges.*
import kotlin.math.log


class CareAssistantChargesFragment: Fragment(),CareAssistantChargesContract.CareAssistantChargesVies {

    private lateinit var mPresenter: CareAssistantChargesContract.CareAssistantChargesPresenter
    private lateinit var mAdapter: CareAssistantChargesAdapter



    override fun clearTextAndFocus() {
        care_assistant_charges_add_panel_login.setText("")
        care_assistant_charges_add_panel_login.clearFocus()
    }

    override fun showCareAssistantCharges() {
        care_assistant_charges_not_found_frame.visibility=View.GONE
        care_assistant_charges_connection_problem_frame.visibility=View.GONE
        care_assistant_charges_found_frame.visibility=View.VISIBLE

    }

    override fun showCareAssistantChargesNotFound() {
        care_assistant_charges_connection_problem_frame.visibility=View.GONE
        care_assistant_charges_found_frame.visibility=View.GONE
        care_assistant_charges_not_found_frame.visibility=View.VISIBLE
    }

    override fun showConnectionError() {
        care_assistant_charges_found_frame.visibility=View.GONE
        care_assistant_charges_not_found_frame.visibility=View.GONE
        care_assistant_charges_connection_problem_frame.visibility=View.VISIBLE
    }



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_care_assistants_charges, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter= CareAssistantChargesAdapter({
            showRemoveDialog(it)
        }
        )
        care_assistant_charges_found_recycler_view.adapter=mAdapter
        care_assistant_charges_found_recycler_view.layoutManager=LinearLayoutManager(context)

        care_assistant_charges_add_panel_fab.setOnClickListener{
            hideKeyboard()
            care_assistant_charges_add_panel_fab.isEnabled=false
            val login = care_assistant_charges_add_panel_login.text.toString();
            mPresenter.addCharge(login)
            care_assistant_charges_add_panel_fab.isEnabled=true
        }


    }

    private fun hideKeyboard() {
        val view = InteractionsFragment@ this.activity.currentFocus
        if (view != null) {
            val imm = InteractionsFragment@ this.activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    override fun setPresenter(presenter: CareAssistantChargesContract.CareAssistantChargesPresenter) {
        mPresenter=presenter
    }

    override fun loadCharges(charges: List<PatientDTO>) {
        if( charges.isEmpty() )
            showCareAssistantChargesNotFound()
        else {
            mAdapter.loadItems(charges)
            showCareAssistantCharges()
        }

    }


    override fun showSnackBarError(error: String) {
        Snackbar.make(care_assistant_charges_root,error, Snackbar.LENGTH_LONG).show();
    }


    fun showRemoveDialog(patientDTO: PatientDTO) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Usuwanie")
                .setMessage("Czy na pewno chcesz usunąć ${patientDTO.name} ${patientDTO.lastName}?")
                .setPositiveButton(android.R.string.yes, { _, _ ->
                    mPresenter.deleteCharge(patientDTO)
                })
                .setNegativeButton(android.R.string.no, { _, _ ->
                })
                .setIcon(R.drawable.ic_error_outline_black_24px)
                .show()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

}