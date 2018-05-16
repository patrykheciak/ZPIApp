package com.zpi.zpiapp.interactions

import android.util.Log
import com.zpi.zpiapp.model.Drug
import com.zpi.zpiapp.model.Interaction
import com.zpi.zpiapp.utlis.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InteractionsPresenter(val mInteractionsView: InteractionsContract.View) : InteractionsContract.Presenter {
    private lateinit var drugs: List<Drug>

    init {
        mInteractionsView.setPresenter(this)
    }

    override fun start() {

        RetrofitInstance.interactionsService
                .drugList()
                .enqueue(object : Callback<List<Drug>> {
                    override fun onResponse(call: Call<List<Drug>>?, response: Response<List<Drug>>?) {
                        Log.d("InteractionPresenter", response?.body().toString())
                        if (response != null) {
                            if (response.isSuccessful) {
                                val body = response.body()
                                body?.let {
                                    drugs = it
                                    mInteractionsView.setDrugs(drugs)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Drug>>?, t: Throwable?) {
                        Log.d("InteractionsPresenter", "" + t.toString())
                        call?.clone()?.enqueue(this)
                    }

                })
    }

    override fun interact(drug1: String, drug2: String) {
        if (::drugs.isInitialized.not()){
            mInteractionsView.showError()
            return
        }

        val d1 = findDrugByName(drug1)
        val d2 = findDrugByName(drug2)

        if (d1 == null)
            mInteractionsView.showNoSuchDrug(1)
        if (d2 == null)
            mInteractionsView.showNoSuchDrug(2)

        if (d1 != null && d2 != null) {
            // ask API for interaction
            RetrofitInstance.interactionsService
                    .interact(d1.idDrug, d2.idDrug)
                    .enqueue(object : Callback<List<Interaction>> {

                        override fun onResponse(call: Call<List<Interaction>>?, response: Response<List<Interaction>>?) {

                            response?.let {
                                if (it.isSuccessful) {
                                    val interactions = it.body()
                                    interactions?.let {
                                        if (it.isNotEmpty())
                                            mInteractionsView.showInteractions(it)
                                        else
                                            mInteractionsView.showNoInteractions()
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<List<Interaction>>?, t: Throwable?) {
                            Log.e("InteractonsPresenter", t.toString())
                            mInteractionsView.showError()
                        }
                    })
        } else {
            mInteractionsView.hideInteractionSection()
        }
    }

    private fun findDrugByName(drug1: String): Drug? {
        return drugs.firstOrNull { it.name == drug1 }
    }
}