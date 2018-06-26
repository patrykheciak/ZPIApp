package com.zpi.zpiapp.editTodayDrugs


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zpi.zpiapp.R
import kotlinx.android.synthetic.main.fragment_today_drugs.*


class EditTodayDrugsFragment : Fragment(),EditTodayDrugsContract.View {


    private lateinit var mPresenter: EditTodayDrugsContract.Presenter
    private lateinit var mPatientDrugRowAdapter: PatientDrugRowAdapter

    override fun setPresenter(presenter: EditTodayDrugsContract.Presenter) {
        mPresenter=presenter
    }

    override fun setPatientDrugsRow(patientDrugRow: List<PatientDrugRow>) {
        mPatientDrugRowAdapter.load(patientDrugRow)
    }

    override fun showSnackBarMessage(message: String) {
        Snackbar.make(todayDrugRV, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_today_drugs, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPatientDrugRowAdapter = PatientDrugRowAdapter(onButtonClick = mPresenter::editPatientDrug)
        todayDrugRV.adapter = mPatientDrugRowAdapter
        todayDrugRV.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun updatePatientDrugRow(patientDrugRow: PatientDrugRow) {
        mPatientDrugRowAdapter.update(patientDrugRow)
    }

    override fun showSnackBarError(text: String) {
        Snackbar.make(todayDrugRV, text, Snackbar.LENGTH_LONG).show()
    }

}
