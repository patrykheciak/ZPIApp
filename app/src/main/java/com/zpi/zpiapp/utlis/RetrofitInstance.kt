package com.zpi.zpiapp.utlis

import com.zpi.zpiapp.careAssistants.CareAssistantsAdapter
import com.zpi.zpiapp.data.CareAssistantsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

//http://localhost:49822/
object RetrofitInstance {
    private const val url: String = "http://192.168.43.98:49823/"

    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()!!

    val careAssistantsService = retrofit.create(CareAssistantsService::class.java)!!

}