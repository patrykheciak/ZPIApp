package com.zpi.zpiapp.editPatientDrug

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import com.zpi.zpiapp.model.Drug
import com.zpi.zpiapp.model.PatientDrug
import com.zpi.zpiapp.model.Physician
import java.util.*

interface EditPatientDrugContract {
    interface View : BaseView<Presenter> {
        fun setDrugs(drugs: List<Drug>)
        fun setPhysicians(physicians: List<Physician>)
        fun setPhysicianPwz(pwz: String)
        fun showSnackBarError(error: String)

        fun drugNotExistError()
        fun pwzNotExistError()
        fun dateError()

        fun close()
    }

    interface Presenter : BasePresenter {
        fun setData(patientDrug: PatientDrug)
        fun sendData()

        fun setDrugName(drugName: String)
        fun setPhysiciansPwz(physiciansPwz: String)
        fun setStartDate(startData: Calendar)
        fun setEndDate(endData: Calendar)
        fun setMorning(morning: String)
        fun setMidday(midday: String)
        fun setNight(night: String)
        fun setAnnotation(annotation: String)
        fun setHasEndDate(hasEndDate: Boolean)

        fun onViewDestroyed()
    }

}
