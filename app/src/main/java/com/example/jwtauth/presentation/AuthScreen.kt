package com.example.jwtauth.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jwtauth.auth.AuthResult

@Composable
fun authScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
){
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(viewModel, context){
    viewModel.authChannel.collect{result->
        when(result){
            is AuthResult.Authorized ->{
            navController.navigate(Screen.SecretScreen.route){
                popUpTo(Screen.AuthScreen.route){
                    inclusive = true
                }
            }
            }
            is AuthResult.UnAuthorized ->{
                Toast.makeText(
                    context,
                    "You are not authorized",
                    Toast.LENGTH_LONG
                ).show()
            }
            is AuthResult.UnknownError ->{
                Toast.makeText(
                    context,
                    "An Unknown error occurred",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = state.signUpUsername, onValueChange = {
            viewModel.onEvent(
                AuthEvent.SignUpUsernameChange(it)
            )},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Username")
            }
         )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = state.signUpPassword, onValueChange ={
            viewModel.onEvent(
                AuthEvent.SignUpPasswordChange(it)
            )
        },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password")
            } )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            viewModel.onEvent(AuthEvent.SignUp)
        },
            modifier = Modifier.align(Alignment.End)) {
            Text(text = "SingUp")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(value =  state.signInUsername, onValueChange = {
            viewModel.onEvent(
                AuthEvent.SignInUsernameChange(it)
            )
        },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Username")
            })

        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = state.signInPassword , onValueChange = {
            viewModel.onEvent(
                AuthEvent.SignInPasswordChange(it)
            )
        },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password")
            })

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { viewModel.onEvent(AuthEvent.SignIn) },
            modifier = Modifier.align(Alignment.End)) {
            Text(text = "SingIn")
        }



    }

}