package city.zouitel.init

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.ProcessLifecycleInitializer
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WorkManagerInitializer : Initializer<WorkManager> {

    @Provides
    @Singleton
    override fun create(@ApplicationContext context: Context): WorkManager {
        val workFactory = getWorkerFactory(context)
        val configuration = Configuration.Builder()
            .setWorkerFactory(workFactory)
            .build()
        WorkManager.initialize(context, configuration)
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): MutableList<Class<ProcessLifecycleInitializer>> {
        return mutableListOf(
            ProcessLifecycleInitializer::class.java
        )
    }

    private fun getWorkerFactory(appContext: Context): HiltWorkerFactory {
        val workManagerEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            WorkManagerInitializerEntryPoint::class.java
        )
        return workManagerEntryPoint.hiltWorkerFactory()
    }
}