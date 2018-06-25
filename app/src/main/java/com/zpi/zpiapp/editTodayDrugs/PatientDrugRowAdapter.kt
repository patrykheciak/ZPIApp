package com.zpi.zpiapp.editTodayDrugs

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zpi.zpiapp.R
import com.zpi.zpiapp.utlis.Utils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.today_drugs_row.*


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
            todayDrugDrugName.text = patientDrugRow.drugName
            patientDrugRow.drugDose.let {
                if (it.isEmpty())
                    Utils.hideView(todayDrugDrugDose)
                else
                    todayDrugDrugDose.text= "Dawka: " + patientDrugRow.drugDose
            }
            patientDrugRow.patientDrugAnnotation.let {
                if (it.isEmpty())
                    Utils.hideView(todayDrugPatientDrugAnnotation)
                else
                    todayDrugPatientDrugAnnotation.text = patientDrugRow.patientDrugAnnotation
            }
            todayDrugMorningBtn.text= patientDrugRow.morning.toString()
            todayDrugMiddayBtn.text = patientDrugRow.midday.toString()
            todayDrugNightBtn.text = patientDrugRow.night.toString()
        }

    }

}