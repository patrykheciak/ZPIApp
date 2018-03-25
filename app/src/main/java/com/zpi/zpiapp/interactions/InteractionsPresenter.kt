package com.zpi.zpiapp.interactions

import android.util.Log
import com.zpi.zpiapp.data.InteractionsService
import com.zpi.zpiapp.model.Drug
import com.zpi.zpiapp.model.Interaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InteractionsPresenter(val mInteractionsView: InteractionsContract.View) : InteractionsContract.Presenter {
    private lateinit var drugs: Array<Drug>

    init {
        mInteractionsView.setPresenter(this)
    }

    override fun start() {
        drugs = Mock.drugs()
        mInteractionsView.setDrugs(drugs)
    }

    override fun interact(drug1: String, drug2: String) {
        val d1 = findDrugByName(drug1)
        val d2 = findDrugByName(drug2)

        if (d1 == null)
            mInteractionsView.showNoSuchDrug(1)
        if (d2 == null)
            mInteractionsView.showNoSuchDrug(2)

        if (d1 != null && d2 != null) {
            // ask API for interaction

            // probably we need to make it singleton and maybe put it in some better place
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.1.105:49823/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            retrofit.create(InteractionsService::class.java)
                    .interact(d1.id, d2.id)
                    .enqueue(object : Callback<List<Interaction>> {

                override fun onResponse(call: Call<List<Interaction>>?, response: Response<List<Interaction>>?) {

                    response?.let { if (it.isSuccessful) {
                        val interactions = it.body()
                        interactions?.let {
                            if (it.isNotEmpty())
                                mInteractionsView.showInteractions(it)
                            else
                                mInteractionsView.showNoInteractions()
                        }
                    }}
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