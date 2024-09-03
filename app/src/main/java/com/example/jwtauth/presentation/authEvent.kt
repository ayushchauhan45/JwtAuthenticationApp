package com.example.jwtauth.presentation

sealed class AuthEvent {
    data class SignUpUsernameChange(val value:String): AuthEvent()
    data class SignUpPasswordChange(val value:String):AuthEvent()
    data object SignUp : AuthEvent()

    data class SignInUsernameChange(val value:String): AuthEvent()
    data class SignInPasswordChange(val value:String): AuthEvent()
    data object SignIn: AuthEvent()
}