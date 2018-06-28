package com.zpi.zpiapp.drugHistory

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.PatientDrug
import com.zpi.zpiapp.utlis.RetrofitInstance
import com.zpi.zpiapp.utlis.User
import kotlinx.android.synthetic.main.fragment_screen_slide_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ScreenSlidePageFragment : Fragment() {

    var numer = 0
    val adapter = DrugHistoryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_screen_slide_page, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerDrugHistory.adapter = adapter
        recyclerDrugHistory.layoutManager = LinearLayoutManager(context)
    }


    override fun onResume() {
        super.onResume()
        if(adapter.items.isNotEmpty())
            progressDrugHistory.visibility = View.GONE

    }

    fun setDrugHistoryDay(date: Date) {
        val c = Calendar.getInstance()
        c.time = date
        RetrofitInstance
                .patientDrugService
                .patientsDrugsInDay(
                        User.userId,
                        "${c.get(Calendar.YEAR)}-${c.get(Calendar.MONTH) + 1}-${c.get(Calendar.DAY_OF_MONTH)}")
                .enqueue(object : Callback<List<PatientDrug>> {

                    override fun onFailure(call: Call<List<PatientDrug>>?, t: Throwable?) {
                        Log.d("DrugHistoryPresenter", "" + t.toString())
                        call?.clone()?.enqueue(this)
                    }

                    override fun onResponse(call: Call<List<PatientDrug>>?, response: Response<List<PatientDrug>>?) {
                        Log.d("DrugHistoryPresenter", response?.body().toString())
                        if (response != null) {
                            if (response.isSuccessful) {
                                val body = response.body()
                                body?.let {
                                    adapter.updatePatientDrugs(it)
                                    adapter.notifyDataSetChanged()
                                }

                                progressDrugHistory?.visibility = View.GONE
                            }
                        }
                    }
                })    }

}
