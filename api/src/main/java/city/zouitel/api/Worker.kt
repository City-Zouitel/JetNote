package city.zouitel.api

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class Worker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val application: Application,
    private val firestoreRepoImp: FirestoreRepoImp
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            println("""
                *************************************************************
                *************************************************************
                *************************************************************
                ${inputData.getString("title_data") ?: "null"}
                *************************************************************
                *************************************************************
                *************************************************************
            """.trimIndent())

            Result.success()
        } catch (e: Exception) {
            Result.success()
        }
    }
}