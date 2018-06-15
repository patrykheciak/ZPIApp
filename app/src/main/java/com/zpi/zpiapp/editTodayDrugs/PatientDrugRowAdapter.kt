package com.zpi.zpiapp.editTodayDrugs

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zpi.zpiapp.R
import com.zpi.zpiapp.drugHistory.DrugHistoryAdapter
import kotlinx.android.extensions.LayoutContainer


class PatientDrugRowAdapter(val items: MutableList<PatientDrugRow> = mutableListOf()):RecyclerView.Adapter<PatientDrugRowAdapter.PatientDrugVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientDrugVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.today_drugs_row,parent,false)
        return PatientDrugVH(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PatientDrugVH, position: Int) {
       holder.bind(items[position])
    }

    fun load(patientDrugRowList: List<PatientDrugRow>){
        items.addAll(patientDrugRowList)
        patientDrugRowList.sortedBy { it.drugName }
        notifyDataSetChanged()
    }

    inner class PatientDrugVH(override val containerView: View):RecyclerView.ViewHolder(containerView),LayoutContainer {
        fun bind( patientDrugRow:PatientDrugRow ){

        }
    }

}