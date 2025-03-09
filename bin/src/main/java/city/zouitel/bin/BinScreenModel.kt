package city.zouitel.bin

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy.KEEP
import androidx.work.NetworkType
import androidx.work.WorkManager
import cafe.adriel.voyager.core.model.ScreenModel

class BinScreenModel(application: Application): ScreenModel {

    private var workManager = WorkManager.getInstance(application)

    fun initBinWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresStorageNotLow(true)
            .build()

        val request = androidx.work.OneTimeWorkRequestBuilder<BinWorker>()
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(WORK_NAME, KEEP, request)
    }

    companion object {
        const val WORK_NAME = "unique_bin_work"
    }
}