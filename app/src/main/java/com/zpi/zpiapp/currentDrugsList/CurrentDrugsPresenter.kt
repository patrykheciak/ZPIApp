package com.zpi.zpiapp.currentDrugsList

import com.zpi.zpiapp.model.PatientDrug
import com.zpi.zpiapp.utlis.RetrofitInstance
import com.zpi.zpiapp.utlis.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentDrugsPresenter(private var currentDrugsView:CurrentDrugsContract.View?):CurrentDrugsContract.Presenter{
    private val currentDrugsList:MutableList<PatientDrug> = mutableListOf()

    override fun onViewDestroyed() {
        currentDrugsView= null
    }

    init {
        currentDrugsView?.setPresenter(this)
    }

    override fun start() {
        loadDrugs()
    }

    override fun loadDrugs() {
        currentDrugsList.clear()
        RetrofitInstance.patientDrugService
                .currentPatientsDrugs(User.userId)
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
                        if(currentDrugsView!=null)
                            call?.clone()?.enqueue(this)
                    }
                } )
    }

    override fun deleteDrug(idPd: Int) {
        RetrofitInstance.patientDrugService
                .removePatientDrug(idPd)
                .enqueue(object :Callback<ResponseBody>{

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        response?.let {
                            if (it.isSuccessful){
                                removeByIdPd(idPd)
                                currentDrugsView?.showTakenDrugs(currentDrugsList)
                            } else{
                                currentDrugsView
                                        ?.showSnackBarError(it.errorBody()?.string()?:"Nieznany błąd")
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        currentDrugsView?.showSnackBarError(t.toString())
                    }

                })
    }

    private fun removeByIdPd(idPd: Int) {
        val index = currentDrugsList.indexOfFirst { it.idPatientDrug == idPd }
        currentDrugsList.removeAt(index)
    }


}
