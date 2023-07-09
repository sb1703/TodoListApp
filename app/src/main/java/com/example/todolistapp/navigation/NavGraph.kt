package com.example.todolistapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todolistapp.TodoEvent
import com.example.todolistapp.screens.homeScreen.TodoScreen
import com.example.todolistapp.TodoState
import com.example.todolistapp.screens.animatedSplashScreen.AnimatedSplashScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    state: TodoState,
    onEvent: (TodoEvent)->Unit
){
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ){
        composable(
            route = Screen.SplashScreen.route
        ){
            AnimatedSplashScreen(navController = navController)
        }
        composable(
            route = Screen.HomeScreen.route
        ){
            TodoScreen(state = state , onEvent = onEvent)
        }
    }
}