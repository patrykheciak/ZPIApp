package com.zpi.zpiapp.data

import com.zpi.zpiapp.model.CareAssistant
import com.zpi.zpiapp.model.PatientDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CareAssistantsService{

    @GET("$route/careassistants/{userId}")
    fun patientCareAssistants(@Path("userId") userId: Int): Call<List<CareAssistant>>

    @POST("$route/add/{userId}/{login}")
    fun addCareAssistant(@Path("userId") userId: Int,
                         @Path("login") careAssistantLogin: String):Call<ResponseBody>

    @DELETE("$route/remove/{userId}/{caId}")
    fun removeCareAssistant(@Path("userId") userId: Int,
                            @Path("caId") careAssistantId:Int):Call<ResponseBody>

    @GET("$route/patients/{caId}")
    fun getCharges(@Path("caId") careAssistantId:Int ):Call<List<PatientDTO>>

    companion object {
        const val route = "api/PatientCareAssistant"
    }
}


