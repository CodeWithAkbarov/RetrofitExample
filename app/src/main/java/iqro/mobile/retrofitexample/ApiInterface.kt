package iqro.mobile.retrofitexample

import iqro.mobile.retrofitexample.todo.Todo
import iqro.mobile.retrofitexample.user.UserCreate
import iqro.mobile.retrofitexample.user.UserListResponse
import iqro.mobile.retrofitexample.user.UserResponse
import retrofit2.Response
import retrofit2.http.*

/**
 *Created by Zohidjon Akbarov
 */
interface ApiInterface {
    @GET("/todos")
    suspend fun getTodoList(): Response<List<Todo>>

    @GET("/api/users")
    suspend fun getAllUserByPage(@Query("page") page:Int,@Query("per_page") perPage:Int): Response<UserListResponse>


    @GET("/api/users/{id}")
    suspend fun getUserById(@Path("id") userId:Int):Response<UserResponse>

    @POST("/api/users")
    suspend fun createUser(@Body userCreate: UserCreate):Response<UserCreate>

    @DELETE("/api/users/{id}")
    suspend fun deleteUser(@Path("id") userId:Int):Response<Unit>


}