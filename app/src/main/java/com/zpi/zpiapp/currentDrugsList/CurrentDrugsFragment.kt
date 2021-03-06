package com.zpi.zpiapp.currentDrugsList


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zpi.zpiapp.R
import com.zpi.zpiapp.editPatientDrug.EditPatientDrugActivity
import com.zpi.zpiapp.model.PatientDrug
import kotlinx.android.synthetic.main.fragment_current_drugs.*
import kotlinx.android.synthetic.main.fragment_interactions.*

class CurrentDrugsFragment : Fragment(),CurrentDrugsContract.View {
    private lateinit var mPresenter: CurrentDrugsContract.Presenter
    private lateinit var mCurrentDrugsAdapter: CurrentDrugsAdapter



    override fun setPresenter(presenter: CurrentDrugsContract.Presenter) {
        mPresenter=presenter
    }

    override fun showTakenDrugs(drugs: List<PatientDrug>) {
        mCurrentDrugsAdapter.reloadDrugs(drugs)

    }

    override fun showSnackBarError(error: String) {
        Snackbar.make(fragment_current_drugs_root,error, Snackbar.LENGTH_LONG).show()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_current_drugs, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCurrentDrugsAdapter=CurrentDrugsAdapter(context,onRemoveClick = mPresenter::deleteDrug)
        fragment_current_drugs_recycler_view.adapter=mCurrentDrugsAdapter
        fragment_current_drugs_recycler_view.layoutManager= LinearLayoutManager(context)

        fragment_current_drugs_fab.setOnClickListener {
            startActivity(Intent(this.context,EditPatientDrugActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.onViewPause()
    }

}
