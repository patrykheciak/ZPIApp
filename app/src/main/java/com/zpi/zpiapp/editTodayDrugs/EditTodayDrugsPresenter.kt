package com.zpi.zpiapp.editTodayDrugs

import android.util.Log
import com.zpi.zpiapp.model.PatientDrug
import com.zpi.zpiapp.utlis.RetrofitInstance
import com.zpi.zpiapp.utlis.User.userId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditTodayDrugsPresenter( var mEditTodayDrugsView:EditTodayDrugsContract.View?):EditTodayDrugsContract.Presenter{

    val patientDrugRowList:MutableList<PatientDrugRow> = mutableListOf()

    init {
        mEditTodayDrugsView?.setPresenter(this)
    }

    override fun start() {
        loadDrugRow()
    }

    override fun editPatientDrug(patientDrugRow: PatientDrugRow) {
        RetrofitInstance.patientDrugService
                .postPatientDrugCallendarRow(patientDrugRow.idPatientDrug,patientDrugRow.toCalendarRow())
                .enqueue(object :Callback<Int>{
                    override fun onFailure(call: Call<Int>?, t: Throwable?) {
                        Log.d("ETDPresenter", "" + t.toString())
                    }

                    override fun onResponse(call: Call<Int>?, response: Response<Int>?) {
                        response?.let {
                            if(it.isSuccessful)
                                it.body()?.let {
                                    update(it,patientDrugRow)
                                }
                        }
                    }
                })
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

    fun update(newRowId: Int, patientDrugRow: PatientDrugRow  ){
        val replaceItemIndex = patientDrugRowList.indexOfFirst {
            it.idPatientDrug==patientDrugRow.idPatientDrug
        }
        patientDrugRowList.removeAt(replaceItemIndex)
        patientDrugRowList.add(replaceItemIndex,patientDrugRow)
        patientDrugRow.idRow=newRowId

    }


}
