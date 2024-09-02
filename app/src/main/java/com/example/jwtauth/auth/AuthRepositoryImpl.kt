package com.example.jwtauth.auth

import android.content.SharedPreferences
import android.util.Log
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api:authApi,
    private val prefs:SharedPreferences
):AuthRepository{
    override suspend fun signUp(username: String, password: String): AuthResult<Unit> {
        return try {
           api.singUp(authRequest  = AuthRequest(
               username = username,
               password= password
           ))
            signIn(username,password)
        }catch (e:HttpException){
            if (e.code() == 401){
                AuthResult.UnAuthorized()
            }else{
                AuthResult.UnknownError()
            }
        }catch (e:Exception){
            Log.e("AuthRepository", "Unknown error during sign-up: ${e.message}", e)
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.singIn(authRequest  = AuthRequest(
                username = username,
                password= password
            ))
            prefs.edit().putString("jwt", response.token).apply()
            AuthResult.Authorized()
        }catch (e:HttpException){
            if (e.code() == 401){
                AuthResult.UnAuthorized()
            }else{
                AuthResult.UnknownError()
            }
        }catch (e:Exception){
            AuthResult.UnknownError()
        }

    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null)?: return AuthResult.UnAuthorized()
            api.authenticate("Bearer $token")
            AuthResult.Authorized()
        }catch (e:HttpException){
            if (e.code() == 401){
                AuthResult.UnAuthorized()
            }else{
                AuthResult.UnknownError()
            }
        }catch (e:Exception){

            AuthResult.UnknownError()
        }
    }
}