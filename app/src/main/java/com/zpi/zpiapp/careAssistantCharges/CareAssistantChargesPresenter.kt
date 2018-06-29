package com.zpi.zpiapp.careAssistantCharges

import com.zpi.zpiapp.model.PatientDTO
import com.zpi.zpiapp.utlis.RetrofitInstance
import com.zpi.zpiapp.utlis.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CareAssistantChargesPresenter(var mView: CareAssistantChargesContract.CareAssistantChargesVies?) : CareAssistantChargesContract.CareAssistantChargesPresenter {


    override fun addCharge(login: String) {
        RetrofitInstance.careAssistantsService
                .addCharge(User.userId,login)
                .enqueue(object :Callback<PatientDTO>{
                    override fun onFailure(call: Call<PatientDTO>?, t: Throwable?) {
                        mView?.showSnackBarError("Błąd połączenia")
                    }

                    override fun onResponse(call: Call<PatientDTO>?, response: Response<PatientDTO>?) {
                        response?.let {
                            if (it.isSuccessful)
                                it.body()?.let {
                                    mChargesList.add(it)
                                    mView?.loadCharges(mChargesList)
                                }
                            else
                                mView?.showSnackBarError(it.errorBody()?.string()?:"Nieznany błąd")
                        }

                    }

                })
    }

    val mChargesList = mutableListOf<PatientDTO>()

    init {
        mView?.setPresenter(this)
    }


    override fun start() {
        RetrofitInstance
                .careAssistantsService
                .getCharges(User.userId)
                .enqueue(object : Callback<List<PatientDTO>> {
                    override fun onFailure(call: Call<List<PatientDTO>>?, t: Throwable?) {
                        mView?.showConnectionError()
                        if (mView != null)
                            call?.clone()?.enqueue(this)
                    }

                    override fun onResponse(call: Call<List<PatientDTO>>?, response: Response<List<PatientDTO>>?) {
                        response?.let {
                            if (it.isSuccessful) {
                                it.body()?.let {
                                    mChargesList.clear()
                                    mChargesList.addAll(it)
                                }
                            }
                            mView?.loadCharges(mChargesList)
                        }
                    }
                })
    }

    override fun onViewDestroyed() {
        mView = null
    }

    override fun deleteCharge(charge: PatientDTO) {
        RetrofitInstance
                .careAssistantsService
                .removeCareAssistant(charge.idPatient, User.userId)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        mView?.showSnackBarError("Błąd połączenia")
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        response?.let {
                            if (it.isSuccessful) {
                                mChargesList.remove(charge)
                                mView?.loadCharges(mChargesList)
                            } else
                                mView?.showSnackBarError(it.errorBody()?.string()
                                        ?: "Nieznany błąd")
                        }
                    }
                })
    }


}