package com.example.loginsignup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = screen.Home.route) {
        composable(screen.Home.route) {
            AuthScreen(navController)
        }
        composable(screen.SecondScreen.route){
            SecondScreen(navController)
        }

    }
}


sealed class screen(
    val route: String
) {
    object Home : screen("home_screen")
    object SecondScreen : screen("Second_Screen")
}