package com.zpi.zpiapp.data

import com.zpi.zpiapp.model.Interaction
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InteractionsService {

    @GET("api/interactions/{drug1}/{drug2}")
    fun interact(@Path("drug1") idDrug1: Int,
                 @Path("drug2") idDrug2: Int): Call<List<Interaction>>
}