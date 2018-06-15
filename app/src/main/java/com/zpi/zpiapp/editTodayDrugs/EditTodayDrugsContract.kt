package com.zpi.zpiapp.editTodayDrugs

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import com.zpi.zpiapp.model.CalendarRow
import com.zpi.zpiapp.model.PatientDrug

interface EditTodayDrugsContract {
    interface View:BaseView<Presenter>{
        fun setPatientDrugsRow( patientDrugRow:List<PatientDrugRow> )
        fun showSnackBarMessage( message:String )
    }

    interface Presenter:BasePresenter{
        fun editPatientDrug( patientDrugRow:PatientDrugRow )

        fun onViewDestroyed()
    }

}