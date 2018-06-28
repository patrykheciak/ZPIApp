package com.zpi.zpiapp.data

import com.zpi.zpiapp.model.Physician
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PhysiciansService {

    @GET("$route/physicians/{userId}")
    fun patientPhysicians(@Path("userId") userId: Int): Call<List<Physician>>

    @PUT("$route/add/{userId}/{pwzNumber}")
    fun addPhysician(@Path("userId") userId: Int,
                         @Path("pwzNumber") pwzNumber: String): Call<ResponseBody>

    @PUT("$route/remove/{userId}/{psId}")
    fun removePhysician(@Path("userId") userId: Int,
                            @Path("psId") physicianId:Int): Call<ResponseBody>

    @GET( "api/Physicians" )
    fun getAllPhysicians():Call<List<Physician>>

    companion object {
        const val route="api/PatientPhysician"
    }

}