package com.zpi.zpiapp.data

import retrofit2.http.GET
import retrofit2.http.Path
import okhttp3.ResponseBody
import retrofit2.Call

interface UserService {

    @GET("$route/{login}/{password}")
    fun loginPerson(
            @Path("login") login: String,
            @Path("password") password: String
    ): Call<ResponseBody>


    companion object {
        const val route = "api/User"
    }
}