package com.example.jwtauth.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventStart
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jwtauth.auth.AuthRepository
import com.example.jwtauth.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (
    private val authRepository: AuthRepository
): ViewModel()
{
    var state by mutableStateOf(AuthState())
    private val _authChannel = Channel<AuthResult<Unit>>()
    val authChannel = _authChannel.receiveAsFlow()

    init {
        authenticate()
    }

    fun onEvent(event: AuthEvent) {
        when(event) {
            is AuthEvent.SignUpUsernameChange -> {
                state = state.copy(
                    signUpUsername = event.value
                )
            }

            is AuthEvent.SignUpPasswordChange -> {
                state = state.copy(
                    signUpPassword = event.value
                )
            }

            is AuthEvent.SignUp -> {
                signUp()
            }

            is AuthEvent.SignInUsernameChange -> {
                state = state.copy(
                    signInUsername = event.value
                )
            }

            is AuthEvent.SignInPasswordChange -> {
                state = state.copy(
                    signInPassword = event.value
                )
            }

            is AuthEvent.SignIn -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepository.signUp(
                username = state.signUpUsername,
                password = state.signUpPassword
            )
            _authChannel.send(result)
            state = state.copy(isLoading = false)

        }
    }

    private fun signIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepository.signIn(
                username = state.signUpUsername,
                password = state.signInPassword
            )
            _authChannel.send(result)
            state = state.copy(isLoading = false)

        }
    }

    fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = false)
            val result = authRepository.authenticate()
            _authChannel.send(result)
            state = state.copy(isLoading = true)
        }
    }
}





