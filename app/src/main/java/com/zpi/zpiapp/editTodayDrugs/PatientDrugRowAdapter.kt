package com.zpi.zpiapp.editTodayDrugs

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.zpi.zpiapp.R
import com.zpi.zpiapp.utlis.Utils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.today_drugs_row.*


class PatientDrugRowAdapter(val items: MutableList<PatientDrugRow> = mutableListOf(),val onButtonClick:(PatientDrugRow)->Unit):RecyclerView.Adapter<PatientDrugRowAdapter.PatientDrugVH>() {

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
        items.addAll(patientDrugRowList.sortedBy { it.drugName })
        notifyDataSetChanged()
    }

    fun update( patientDrugRow:PatientDrugRow ){
        val replaceItemIndex = items.indexOfFirst {
            it.idPatientDrug==patientDrugRow.idPatientDrug
        }
        items[replaceItemIndex] = patientDrugRow
        notifyDataSetChanged()
    }

    inner class PatientDrugVH(override val containerView: View):RecyclerView.ViewHolder(containerView),LayoutContainer {
        fun bind( patientDrugRow:PatientDrugRow ){
            unlockBtn()
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

            setButton(todayDrugMorningBtn,patientDrugRow.morning,patientDrugRow.hasMorning)
            setButton(todayDrugMiddayBtn,patientDrugRow.midday,patientDrugRow.hasMidday)
            setButton(todayDrugNightBtn,patientDrugRow.night,patientDrugRow.hasNight)

            todayDrugMorningBtn.setOnClickListener{
                lockBtn()
                onButtonClick(patientDrugRow.copy().also { it.hasMorning=!it.hasMorning }) }
            todayDrugMiddayBtn.setOnClickListener{
                lockBtn()
                onButtonClick(patientDrugRow.copy().also { it.hasMidday=!it.hasMidday }) }
            todayDrugNightBtn.setOnClickListener{
                lockBtn()
                onButtonClick(patientDrugRow.copy().also { it.hasNight=!it.hasNight }) }
        }

        private fun setButton(btn:Button, value:Int, isActive:Boolean ) {
            btn.text = value.toString()
            when {
                value == 0 -> {
                    btn.isFocusable=false
                    btn.isClickable=false
                    btn.isEnabled=false
                }
                isActive -> btn.setBackgroundResource(R.color.activeBtnTodayDrugs)
                else -> btn.setBackgroundResource(R.color.inactiveBtnTodayDrugs)
            }
        }

        private fun lockBtn(){
            listOf(todayDrugMorningBtn,todayDrugMiddayBtn,todayDrugNightBtn).map { it.isEnabled = false }
        }

        private fun unlockBtn(){
            listOf(todayDrugMorningBtn,todayDrugMiddayBtn,todayDrugNightBtn).map { it.isEnabled = true }
        }


    }

}