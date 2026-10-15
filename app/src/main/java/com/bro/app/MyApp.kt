package com.bro.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun MyApp() {
    // Use dark theme as default
    val darkColors = androidx.compose.material3.darkColorScheme()
    MaterialTheme(
        colorScheme = darkColors
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var currentScreen by remember { mutableStateOf<Screen>(Screen.List) }
            when (val screen = currentScreen) {
                is Screen.List -> AnimationListScreen(onAnimationSelected = { type ->
                    currentScreen = Screen.Detail(type)
                })
                is Screen.Detail -> AnimationDetailScreen(
                    animationType = screen.type,
                    onBack = { currentScreen = Screen.List }
                )
            }
        }
    }
}
