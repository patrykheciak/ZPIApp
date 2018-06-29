package com.zpi.zpiapp.careAssistantCharges

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.PatientDTO
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.charge_row.*

class CareAssistantChargesAdapter(val onRemoveButtonClicked:(PatientDTO)->Unit,
                                  var items: MutableList<PatientDTO> = mutableListOf())
    : RecyclerView.Adapter<CareAssistantChargesAdapter.CareAssistantChargesVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareAssistantChargesVH {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.charge_row,parent,false)
        return CareAssistantChargesVH(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CareAssistantChargesVH, position: Int) {
        holder.bind(items[position])
    }

    fun loadItems( chargesList: List<PatientDTO> ){
        items.clear()
        items.addAll(chargesList.sortedBy { "${it.lastName} ${it.name}" })
        notifyDataSetChanged()
    }

    inner class CareAssistantChargesVH(override val containerView:View):RecyclerView.ViewHolder(containerView),LayoutContainer {
        fun bind(patient: PatientDTO){
            care_assistants_charge_remove_button.isEnabled=true
            care_assistants_charge_row_name.text="${patient.name} ${patient.lastName}"

            care_assistants_charge_remove_button.setOnClickListener({
                care_assistants_charge_remove_button.isEnabled=false
                onRemoveButtonClicked(patient)
                care_assistants_charge_remove_button.isEnabled=true
            })
        }
    }


}