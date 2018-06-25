package com.zpi.zpiapp.model

import java.io.Serializable
import java.util.*

data class PatientDrug(
        var idPatientDrug: Int,
        var idPatient: Int,
        var idDrug: Int,
        var idPhysician: Int?,
        var drugName: String,
        var drugDose: String,
        var dateStart: Date,
        var dateStop: Date?,
        var midday: Int,
        var morning: Int,
        var night: Int,
        var drugAnnotation: String,
        var callendarRows: List<CalendarRow>?
):Serializable