package com.example.graph.home_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import city.zouitel.network.AppNetworkState
import city.zouitel.network.NetworkMonitor
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberNetworkState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor
): AppNetworkState {
    return remember(coroutineScope,networkMonitor) {
        AppNetworkState(coroutineScope, networkMonitor)
    }
}