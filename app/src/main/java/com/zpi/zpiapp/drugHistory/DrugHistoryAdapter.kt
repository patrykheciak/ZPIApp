package com.zpi.zpiapp.drugHistory

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.PatientDrug
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.drug_history_row.*

class DrugHistoryAdapter(var items: List<PatientDrug> = emptyList()) : RecyclerView.Adapter<DrugHistoryAdapter.DrugHistoryVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugHistoryVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.drug_history_row, parent, false)
        return DrugHistoryVH(v)
    }

    override fun onBindViewHolder(holder: DrugHistoryVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updatePatientDrugs(patientDrugs: List<PatientDrug>) {
        items = patientDrugs
        notifyDataSetChanged()
    }

    class DrugHistoryVH(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(patientDrug: PatientDrug) {
            tvPatientDrugName.text = patientDrug.drugName

            cb_morning.isEnabled = patientDrug.morning > 0
            cb_midday.isEnabled = patientDrug.midday > 0
            cb_night.isEnabled = patientDrug.night > 0

            patientDrug.callendarRows?.let {
                var isAnnotated = false
                val sb = StringBuffer()
                sb.append("Uwagi:\n")
                for (row in it) {
                    if (row.hasMorning) {
                        cb_morning.isChecked = true
                        if (row.annotation.isNotEmpty()){
                            sb.append("rano - ${row.annotation}")
                            isAnnotated = true
                        }
                    }
                    if (row.hasMidday) {
                        cb_midday.isChecked = true
                        if (row.annotation.isNotEmpty()) {
                            sb.append("południe - ${row.annotation}")
                            isAnnotated = true
                        }
                    }
                    if (row.hasNight) {
                        cb_night.isChecked = true
                        if (row.annotation.isNotEmpty())
                        {
                            sb.append("wieczór - ${row.annotation}")
                            isAnnotated = true
                        }
                    }
                    if (isAnnotated) {
                        tv_annotation.visibility = View.VISIBLE
                        tv_annotation.text = sb.toString()
                    }
                }
            }
        }
    }
}