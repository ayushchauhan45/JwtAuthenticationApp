package com.example.jwtauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jwtauth.presentation.Screen
import com.example.jwtauth.presentation.ScreetScreen
import com.example.jwtauth.presentation.authScreen
import com.example.jwtauth.ui.theme.JwtAuthTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JwtAuthTheme {
                    val navController= rememberNavController()
                NavHost(navController = navController, startDestination = Screen.AuthScreen.route ){
                    composable(Screen.AuthScreen.route){
                        authScreen(navController)
                    }
                    composable(Screen.SecretScreen.route){
                        ScreetScreen()
                    }

                }

                }
            }
        }
    }


