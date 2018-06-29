package com.zpi.zpiapp.data

import com.zpi.zpiapp.model.UserDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    @GET( "$route/userData/{id}" )
    fun getUserData( @Path( "id" ) userId:Int ): Call<UserDTO>

    @PUT( "$route/editPersonalDataNoPass/{id}" )
    fun editPersonalData( @Path( "id" ) userId:Int, @Body userDTO: UserDTO ): Call<ResponseBody>

    companion object {
        const val route="api/user"
    }
}