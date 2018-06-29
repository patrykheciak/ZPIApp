package com.zpi.zpiapp.data
import com.zpi.zpiapp.model.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT

interface UserService {

    @GET("$route/{login}/{password}")
    fun loginPerson(
            @Path("login") login: String,
            @Path("password") password: String
    ): Call<IntArray>

    @GET( "$route/userData/{id}" )
    fun getUserData( @Path( "id" ) userId:Int ): Call<UserDTO>

    @PUT( "$route/editPersonalDataNoPass/{id}" )
    fun editPersonalData( @Path( "id" ) userId:Int, @Body userDTO: UserDTO ): Call<ResponseBody>

    companion object {
        const val route = "api/User"
    }


}