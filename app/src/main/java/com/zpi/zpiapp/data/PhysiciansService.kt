package com.zpi.zpiapp.data

import com.zpi.zpiapp.model.Physician
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PhysiciansService {

    @GET("$route/physician/{userId}")
    fun patientPhysicians(@Path("userId") userId: Int): Call<List<Physician>>

    @PUT("$route/physician/{userId}/{pwzNumber}")
    fun addPhysician(@Path("userId") userId: Int,
                         @Path("pwzNumber") pwzNumber: String): Call<ResponseBody>

    @PUT("$route/physicianRemove/{userId}/{psId}")
    fun removePhysician(@Path("userId") userId: Int,
                            @Path("psId") physicianId:Int): Call<ResponseBody>

    @GET(route)
    fun getAllPhysicians():Call<List<Physician>>

    private companion object {
        const val route="api/Physician"
    }
}