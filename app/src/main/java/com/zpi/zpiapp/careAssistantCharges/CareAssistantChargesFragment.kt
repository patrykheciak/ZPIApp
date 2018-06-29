package com.zpi.zpiapp.careAssistantCharges

import android.app.AlertDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.PatientDTO
import kotlinx.android.synthetic.main.fragment_care_assistants.*
import kotlinx.android.synthetic.main.fragment_care_assistants_charges.*


class CareAssistantChargesFragment: Fragment(),CareAssistantChargesContract.CareAssistantChargesVies {

    private lateinit var mPresenter: CareAssistantChargesContract.CareAssistantChargesPresenter
    private lateinit var mAdapter: CareAssistantChargesAdapter



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
        care_assistants_charges_root.adapter=mAdapter
        care_assistants_charges_root.layoutManager=LinearLayoutManager(context)
    }

    override fun setPresenter(presenter: CareAssistantChargesContract.CareAssistantChargesPresenter) {
        mPresenter=presenter
    }

    override fun loadCharges(charges: List<PatientDTO>) {
        mAdapter.loadItems(charges)
    }


    override fun showSnackBarError(error: String) {
        Snackbar.make(care_assistants_root,error, Snackbar.LENGTH_LONG).show();
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