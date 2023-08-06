package city.zouitel.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkMod {

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: NetworkMonitorImpl
    ): NetworkMonitor

}