package city.zouitel.init

import android.content.Context
import androidx.lifecycle.ProcessLifecycleInitializer
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager

//class WorkManagerInitializer : Initializer<WorkManager> {
//
//    override fun create(context: Context): WorkManager {
//        val configuration = Configuration.Builder().build()
//        WorkManager.initialize(context, configuration)
//        return WorkManager.getInstance(context)
//    }
//
//    override fun dependencies(): MutableList<Class<ProcessLifecycleInitializer>> {
//        return mutableListOf(
//            ProcessLifecycleInitializer::class.java
//        )
//    }
//}