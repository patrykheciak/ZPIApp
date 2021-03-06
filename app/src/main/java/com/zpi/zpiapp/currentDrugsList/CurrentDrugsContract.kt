package com.zpi.zpiapp.currentDrugsList

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import com.zpi.zpiapp.model.Drug
import com.zpi.zpiapp.model.Interaction
import com.zpi.zpiapp.model.PatientDrug

interface CurrentDrugsContract {
    interface View : BaseView<Presenter> {
        fun showTakenDrugs(drugs: List<PatientDrug>)
        fun showSnackBarError(error:String )
    }

    interface Presenter : BasePresenter {
        fun loadDrugs()
        fun deleteDrug( idPd:Int )
        fun onViewPause()
    }
}