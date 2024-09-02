package com.example.jwtauth.auth

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface authApi {
    @POST("singup")
    suspend fun singUp(
        @Body authRequest: AuthRequest
    )
    @POST("singin")
    suspend fun singIn(
        @Body authRequest: AuthRequest
    ):authRespond

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token:String
    )


}