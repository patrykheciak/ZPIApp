package com.zpi.zpiapp.currentDrugsList

import com.zpi.zpiapp.model.PatientDrug
import com.zpi.zpiapp.utlis.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentDrugsPresenter(private var currentDrugsView:CurrentDrugsContract.View?):CurrentDrugsContract.Presenter{


    private val currentDrugsList:MutableList<PatientDrug> = mutableListOf()
    private var userId :Int = 0

    override fun onViewDestroyed() {
        currentDrugsView= null
    }

    init {
        currentDrugsView?.setPresenter(this)
    }

    override fun start() {
        loadDrugs()
    }

    public val testList:List<PatientDrug> = mutableListOf(
    )

    override fun setUserId(userId: Int) {
        this.userId=userId
    }

    override fun loadDrugs() {
        currentDrugsList.clear()
        RetrofitInstance.patientDrugService
                .currentPatientsDrugs(userId)
                .enqueue(object : Callback<List<PatientDrug>> {
                    override fun onResponse(call: Call<List<PatientDrug>>?, response: Response<List<PatientDrug>>?) {
                        response?.let {
                            it.body()?.let {
                                currentDrugsList.addAll(it)
                            }
                        }
                        currentDrugsView?.showTakenDrugs(currentDrugsList)
                    }

                    override fun onFailure(call: Call<List<PatientDrug>>?, t: Throwable?) {
                        currentDrugsView?.showSnackBarError(t.toString())
                        call?.clone()?.enqueue(this)
                    }
                } )
    }

}
