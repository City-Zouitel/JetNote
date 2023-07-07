package city.zouitel.init

import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@EntryPoint
interface WorkManagerInitializerEntryPoint {
    fun hiltWorkerFactory(): HiltWorkerFactory
}