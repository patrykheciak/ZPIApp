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
    var mPause = true
    var connectCounter = 0

    init {
        mEditTodayDrugsView?.setPresenter(this)
    }

    override fun start() {
        mPause=false
        loadDrugRow()

    }

    override fun editPatientDrug(patientDrugRow: PatientDrugRow) {
        if (connectCounter<=0){
            connectCounter = 3
            RetrofitInstance.patientDrugService
                    .postPatientDrugCallendarRow(patientDrugRow.idPatientDrug,patientDrugRow.toCalendarRow())
                    .enqueue(object :Callback<Int>{
                        override fun onFailure(call: Call<Int>?, t: Throwable?) {
                            Log.d("ETDPresenter", "" + t.toString())
                            connectCounter--
                            if( connectCounter>0 && mEditTodayDrugsView!=null && mPause.not() ){
                                call?.clone()?.execute()
                            } else {
                                mEditTodayDrugsView?.showSnackBarError("Brak połączenia")
                            }
                        }

                        override fun onResponse(call: Call<Int>?, response: Response<Int>?) {
                            response?.let {
                                if(it.isSuccessful)
                                    it.body()?.let {
                                        update(it,patientDrugRow)
                                    }
                                connectCounter=0
                            }
                        }
                    })
        }

    }

    override fun onViewPause() {
        mPause=true
    }



    fun loadDrugRow() {
        RetrofitInstance.patientDrugService
                .currentPatientsDrugs(userId)
                .enqueue(object : Callback<List<PatientDrug>> {
                    override fun onResponse(call: Call<List<PatientDrug>>?, response: Response<List<PatientDrug>>?) {
                        response?.let {
                            it.body()?.let {
                                patientDrugRowList.clear()
                                patientDrugRowList.addAll(it.map { PatientDrugRow.create(it) })
                            }
                        }
                        mEditTodayDrugsView?.setPatientDrugsRow(patientDrugRowList)
                    }
                    override fun onFailure(call: Call<List<PatientDrug>>?, t: Throwable?) {
                        mEditTodayDrugsView?.showSnackBarMessage(t.toString())
                        if (mEditTodayDrugsView!=null && mPause.not())
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

        mEditTodayDrugsView?.updatePatientDrugRow(patientDrugRow)

    }


}
