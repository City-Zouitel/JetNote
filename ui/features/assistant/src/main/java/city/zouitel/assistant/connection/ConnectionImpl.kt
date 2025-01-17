package city.zouitel.assistant.connection

//import android.health.connect.datatypes.SexualActivityRecord
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest.Builder
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

class ConnectionImpl(context: Context): Connection {
    override val isOnline: Flow<Boolean> = callbackFlow {
        val callback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                channel.trySend(true)
            }

            override fun onLost(network: Network) {
                channel.trySend(false)
            }
        }
        val connectivityManager = context.getSystemService<ConnectivityManager>()
        connectivityManager?.registerNetworkCallback(
            Builder()
                .addCapability(NET_CAPABILITY_INTERNET)
                .build(),
            callback
        )
        channel.trySend(connectivityManager.isCurrentlyConnected())

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(callback)
        }
    }.conflate()

    private fun ConnectivityManager?.isCurrentlyConnected() = when (this) {
        null -> false
        else -> activeNetwork
            ?.let(::getNetworkCapabilities)
            ?.hasCapability(NET_CAPABILITY_INTERNET)
            ?: false
    }
}