package com.example.jwtauth.presentation

sealed class Screen(val route:String){
    data object AuthScreen: Screen("auth_screen")
    data object SecretScreen: Screen("secret_screen")

}