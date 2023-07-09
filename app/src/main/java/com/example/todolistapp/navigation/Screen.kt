package com.example.todolistapp.navigation

sealed class Screen(
    val route: String
){
    object SplashScreen: Screen("splash")
    object HomeScreen: Screen("home")
}
