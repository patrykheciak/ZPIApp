package com.zpi.zpiapp.currentDrugsList

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zpi.zpiapp.R
import com.zpi.zpiapp.editPatientDrug.EditPatientDrugActivity
import com.zpi.zpiapp.model.CareAssistant
import com.zpi.zpiapp.model.PatientDrug
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.currnet_drugs_list_row.*


class CurrentDrugsAdapter(val context:Context, var items:MutableList<PatientDrug> = mutableListOf() ): RecyclerView.Adapter<CurrentDrugsAdapter.CurrentDrugsVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentDrugsVH {
        val  v = LayoutInflater.from(parent.context)
                .inflate(R.layout.currnet_drugs_list_row,parent,false)

        return CurrentDrugsVH(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CurrentDrugsVH, position: Int) {
        holder.bind(items[position])
    }

    fun reloadDrugs( drugs:List<PatientDrug> ){
        items.clear()
        items.addAll(drugs)
        this.notifyDataSetChanged()
    }

    inner class CurrentDrugsVH(override val containerView: View):LayoutContainer,RecyclerView.ViewHolder(containerView){
        fun bind(patientDrug:PatientDrug){
            current_drug_row_name.text = patientDrug.drugName
            current_drug_row_edit_button.setOnClickListener {
                val intent = Intent(context,EditPatientDrugActivity::class.java)
                intent.putExtra(EditPatientDrugActivity.EXTRA_PATIENT_DRUG,patientDrug)
                context.startActivity(intent)
            }
        }
    }
}