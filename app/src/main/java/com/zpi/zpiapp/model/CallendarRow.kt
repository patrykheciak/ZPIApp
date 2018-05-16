package com.zpi.zpiapp.model

import java.util.*

data class CalendarRow(
        var idRow: Int,
        var idPatientDrug: Int,
        var hasMorning: Boolean,
        var hasMidday: Boolean,
        var hasNight: Boolean,
        var date: Date,
        var annotation: String
)