package com.zpi.zpiapp.data


import com.zpi.zpiapp.model.CalendarRow
import com.zpi.zpiapp.model.PatientDrug
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PatientDrugService {
    @GET("api/patientdrug/{patientId}")
    fun allPatientsDrugs(@Path("patientId") patientId: Int): Call<List<PatientDrug>>

    @GET("api/patientdrug/currentDrugs/{patientId}")
    fun currentPatientsDrugs(@Path("patientId") patientId: Int): Call<List<PatientDrug>>

    @GET("api/patientdrug/drugsByDate/{patientId}/{day}")
    fun patientsDrugsInDay(
            @Path("patientId") patientId: Int,
            @Path("day") date: String
    ): Call<List<PatientDrug>>

    @POST("api/PatientDrug/postDrug")
    fun addNewPatientDrug(@Body patientDrug: PatientDrug):Call<ResponseBody>

    @PUT("api/PatientDrug/{pdId}")
    fun updatePatientDrug( @Path("pdId") pdId:Int, @Body patientDrug: PatientDrug ):Call<ResponseBody>

    @POST("api/PatientDrug/drugCallendarRow/{idPd}")
    fun postPatientDrugCallendarRow( @Path("idPd")idPd:Int,@Body patientDrugRow:CalendarRow):Call<Int>

    @DELETE("api/PatientDrug/{idPd}")
    fun removePatientDrug( @Path("idPd")idPd:Int ):Call<ResponseBody>
}