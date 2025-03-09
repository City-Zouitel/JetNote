package city.zouitel.bin

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import city.zouitel.domain.usecase.MediaUseCase
import city.zouitel.domain.usecase.NoteAndTagUseCase
import city.zouitel.domain.usecase.ReminderUseCase
import city.zouitel.domain.usecase.TaskUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Suppress("UNREACHABLE_CODE")
class BinWorker(
    private val context: Context,
    params: WorkerParameters,
    private val deleteMediaDrafts: MediaUseCase.DeleteDrafts,
    private val getMediaDrafts: MediaUseCase.GetDrafts,
    private val deleteNoteAndTagDrafts: NoteAndTagUseCase.DeleteDrafts,
    private val deleteReminderDrafts: ReminderUseCase.DeleteDrafts,
    private val deleteTaskDrafts: TaskUseCase.DeleteDrafts,
    private val ioDispatcher: CoroutineDispatcher,
) : CoroutineWorker(context, params) {
    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result = withContext(ioDispatcher){
        return@withContext try {
            getMediaDrafts().forEach {
                context.contentResolver.delete(Uri.parse(it), null, null)
            }
            deleteMediaDrafts()
            deleteNoteAndTagDrafts()
            deleteReminderDrafts()
            deleteTaskDrafts()

            return@withContext Result.Success()
        } catch (e: Exception) {
            return@withContext Result.failure()
        }
    }
}