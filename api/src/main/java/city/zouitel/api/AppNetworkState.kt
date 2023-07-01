package city.zouitel.api

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@Stable
class AppNetworkState(
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor
) {
    val isOnline = networkMonitor.isOnline
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )
}