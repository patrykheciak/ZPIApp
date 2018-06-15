package com.zpi.zpiapp.editTodayDrugs

import com.zpi.zpiapp.model.PatientDrug
import com.zpi.zpiapp.utlis.RetrofitInstance
import com.zpi.zpiapp.utlis.User.userId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditTodayDrugsPresenter( var mEditTodayDrugsView:EditTodayDrugsContract.View?):EditTodayDrugsContract.Presenter{

    val patientDrugRowList:MutableList<PatientDrugRow> = mutableListOf()


    override fun start() {
        loadDrugRow()
    }

    override fun editPatientDrug(patientDrugRow: PatientDrugRow) {

    }

    override fun onViewDestroyed() {
        mEditTodayDrugsView=null
    }



    fun loadDrugRow() {
        patientDrugRowList.clear()
        RetrofitInstance.patientDrugService
                .currentPatientsDrugs(userId)
                .enqueue(object : Callback<List<PatientDrug>> {
                    override fun onResponse(call: Call<List<PatientDrug>>?, response: Response<List<PatientDrug>>?) {
                        response?.let {
                            it.body()?.let {
                                patientDrugRowList.addAll(it.map { PatientDrugRow.create(it) })
                            }
                        }
                        mEditTodayDrugsView?.setPatientDrugsRow(patientDrugRowList)
                    }

                    override fun onFailure(call: Call<List<PatientDrug>>?, t: Throwable?) {
                        mEditTodayDrugsView?.showSnackBarMessage(t.toString())
                        if (mEditTodayDrugsView!=null)
                            call?.clone()?.enqueue(this)
                    }
                } )
    }


}
