package com.zpi.zpiapp.utlis

import com.zpi.zpiapp.data.CareAssistantsService
import com.zpi.zpiapp.data.InteractionsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//http://localhost:49822/
object RetrofitInstance {
    private const val url: String = "http://192.168.0.31:49823/"

    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()!!

    val careAssistantsService = retrofit.create(CareAssistantsService::class.java)!!
    val interactionsService = retrofit.create(InteractionsService::class.java)!!

}