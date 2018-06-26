package com.zpi.zpiapp.data

import com.zpi.zpiapp.model.CareAssistant
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CareAssistantsService{

    @GET("api/patients/careassistants/{userId}")
    fun patientCareAssistants(@Path("userId") userId: Int): Call<List<CareAssistant>>

    @POST("api/patients/careassistantAdd/{userId}/{login}")
    fun addCareAssistant(@Path("userId") userId: Int,
                         @Path("login") careAssistantLogin: String):Call<ResponseBody>

    @DELETE("api/patients/careassistantRemove/{userId}/{caId}")
    fun removeCareAssistant(@Path("userId") userId: Int,
                            @Path("caId") careAssistantId:Int):Call<ResponseBody>
}