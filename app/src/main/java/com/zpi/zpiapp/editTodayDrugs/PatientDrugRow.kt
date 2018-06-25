package com.zpi.zpiapp.editTodayDrugs

import com.zpi.zpiapp.model.CalendarRow
import com.zpi.zpiapp.model.PatientDrug
import java.util.*


data class PatientDrugRow(
        var idRow: Int?,
        val idPatientDrug: Int,
        var hasMorning: Boolean,
        var hasMidday: Boolean,
        var hasNight: Boolean,
        var rowAnnotation: String,
        val drugName: String,
        val drugDose: String,
        val morning: Int,
        val midday: Int,
        val night: Int,
        val patientDrugAnnotation: String) {

    fun toCalendarRow(): CalendarRow {
        return CalendarRow(idRow, idPatientDrug, hasMorning, hasMidday, hasNight, Date(), rowAnnotation)
    }

    companion object {
        fun create(patientDrug: PatientDrug): PatientDrugRow {
            val hasRow = if (patientDrug.callendarRows!=null)
                patientDrug.callendarRows!!.isEmpty().not() else false
            val row = if (hasRow)
                patientDrug.callendarRows!![0] else null

            val idRow = row?.idRow
            val hasMorning = row?.hasMorning ?: false
            val hasMidday = row?.hasMidday ?: false
            val hasNight = row?.hasNight ?: false
            val rowAnnotation = row?.annotation ?: ""

            return PatientDrugRow(
                    idRow,
                    patientDrug.idPatientDrug,
                    hasMorning,
                    hasMidday,
                    hasNight,
                    rowAnnotation,
                    patientDrug.drugName,
                    patientDrug.drugDose,
                    patientDrug.morning,
                    patientDrug.midday,
                    patientDrug.night,
                    patientDrug.drugAnnotation
            )
        }
    }

}