package city.zouitel.api

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.usecase.DataUseCase
import com.example.domain.usecase.TagUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltWorker
class Worker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repo: FirestoreRepoImp,
    private val addTag: TagUseCase.AddTag,
    private val editData: DataUseCase.EditData,
    private val tagMapper: TagMapper,
    private val dataMapper: DataMapper
): CoroutineWorker(context, workerParameters) {

    val englishList: MutableState<DataOrException<List<Info>, Exception>> = mutableStateOf(
        DataOrException(listOf(), Exception(""))
    )

    init {
        getAllEnglishWords()
    }

    private fun getAllEnglishWords() =  CoroutineScope(Dispatchers.IO).launch {
        englishList.value = repo.getAllEnglishWords()
    }
    override suspend fun doWork(): Result {
        return try {
            val title = inputData.getString("title_data")
            val uid = inputData.getString("uid_data") ?: ""

            englishList.value.data?.forEach {
                if (title?.split(' ')?.contains(it.data) == true) {
                    editData.invoke(
                        dataMapper.toDomain(
                            Data().copy(
                                uid = uid,
                                title = "*****",
                            )
                        )
                    )
                }
            }

            Result.success()
        } catch (e: Exception) {
            Result.success()
        }
    }
}