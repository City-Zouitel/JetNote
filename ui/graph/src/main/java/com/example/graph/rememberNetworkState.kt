package com.example.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import city.zouitel.api.AppNetworkState
import city.zouitel.api.NetworkMonitor
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