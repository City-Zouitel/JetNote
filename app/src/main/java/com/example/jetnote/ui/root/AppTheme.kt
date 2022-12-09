package com.example.jetnote.ui.root

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.jetnote.ds.DataStore
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val currentTheme = DataStore(LocalContext.current).isDarkTheme.collectAsState(false).value
    val systemUiController = rememberSystemUiController()
    val isDarkUi = isSystemInDarkTheme()

    val theme = when {
        isSystemInDarkTheme() -> darkColorScheme()
        currentTheme -> darkColorScheme()
        else -> lightColorScheme()
    }

    SideEffect {
        systemUiController.apply {
            setStatusBarColor(Color.Transparent, !isDarkUi)
            setNavigationBarColor(
                if (currentTheme || isDarkUi) Color(red = 28, green = 27, blue = 31)
                else Color(red = 255, green = 251, blue = 254)
            )
        }
    }

    MaterialTheme(colorScheme = theme, content = content)
}