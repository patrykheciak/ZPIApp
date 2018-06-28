package com.zpi.zpiapp.utlis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import com.zpi.zpiapp.data.*


//http://localhost:49822/
//Marcin -> http://192.168.43.98:49823/
//Patryk -> http://192.168.0.31:49823/
object RetrofitInstance {
    private const val url: String = "http://192.168.0.31:49823/"

    var gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()!!

    val careAssistantsService = retrofit.create(CareAssistantsService::class.java)!!
    val physiciansService = retrofit.create(PhysiciansService::class.java)!!
    val interactionsService = retrofit.create(InteractionsService::class.java)!!
    val patientDrugService = retrofit.create(PatientDrugService::class.java)!!
    val userService = retrofit.create(UserService::class.java)!!

}