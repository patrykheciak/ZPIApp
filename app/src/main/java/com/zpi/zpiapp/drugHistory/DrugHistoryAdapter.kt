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

            cbRano.isEnabled = patientDrug.morning > 0
            cbPoludnie.isEnabled = patientDrug.midday > 0
            cbWieczor.isEnabled = patientDrug.night > 0

            patientDrug.callendarRows?.let {
                for (row in it) {
                    if (row.hasMorning)
                        cbRano.isChecked = true
                    if (row.hasMidday)
                        cbPoludnie.isChecked = true
                    if (row.hasNight)
                        cbWieczor.isChecked = true
                    Log.d("DrugHistoryAdapter", "$it")
                }
            }
        }
    }
}