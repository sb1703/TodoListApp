package com.example.todolistapp.screens.animatedSplashScreen

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.todolistapp.R
import com.example.todolistapp.navigation.Screen
import com.example.todolistapp.ui.theme.Purple80
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(
    navController: NavHostController
){

    val isAnimated = remember{ mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if(isAnimated.value) {1f} else {0f},
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    LaunchedEffect(key1 = true){
        isAnimated.value = true
        delay(4000)
        navController.popBackStack()
        navController.navigate(Screen.HomeScreen.route)
    }

    Splash(alpha = alpha)
}

@Composable
fun Splash(
    alpha: Float
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = if (isSystemInDarkTheme()) {
                    Color.Black
                } else {
                    Purple80
                }
            ).alpha(alpha = alpha),
        contentAlignment = Alignment.Center
    ){
        Icon(
            painter = painterResource(id = R.drawable.clipart1705502),
            contentDescription = "Todo Icon",
            tint = Color.White
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSplash(){
    Splash(alpha = 1f)
}