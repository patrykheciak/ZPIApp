package com.zpi.zpiapp.data

import com.zpi.zpiapp.model.Interaction
import com.zpi.zpiapp.model.PatientDrug
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PatientDrugService {
    @GET("api/patientdrug/{patientId}")
    fun allPatientsDrugs(@Path("patientId") patientId: Int): Call<List<PatientDrug>>

    @GET("api/patientdrug/drugsByDate/{patientId}/{day}")
    fun patientsDrugsInDay(
            @Path("patientId") patientId: Int,
            @Path("day") date: String
    ): Call<List<PatientDrug>>
}