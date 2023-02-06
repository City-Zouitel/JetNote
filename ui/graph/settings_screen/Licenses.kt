package com.example.grqph.settings_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.LibraryDefaults

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Licenses(modifier: Modifier = Modifier) {
    Scaffold(
        content = { paddingValues -> OpenSourceContent(modifier = Modifier.padding(paddingValues)) },
        modifier = modifier,
    )
}


@Composable
private fun OpenSourceContent(modifier: Modifier = Modifier) {
    LibrariesContainer(
        modifier = modifier.fillMaxSize(),
        colors = LibraryDefaults.libraryColors(
            backgroundColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            badgeContentColor = MaterialTheme.colorScheme.onPrimary,
            badgeBackgroundColor = MaterialTheme.colorScheme.primary,
        ),
    )
}
