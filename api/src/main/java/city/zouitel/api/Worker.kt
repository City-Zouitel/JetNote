package city.zouitel.api

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.viewModelScope
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.usecase.DataUseCase
import com.example.domain.usecase.NoteUseCase
import com.example.domain.usecase.TagUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltWorker
class Worker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repo: FirestoreRepoImp,
    private val getAllUnVerifiedNotes: NoteUseCase.GetAllUnVerifiedNotes,
    private val editData: DataUseCase.EditData,
    private val dataMapper: DataMapper,
    private val noteMapper: NoteMapper
): CoroutineWorker(context, workerParameters) {

    private val englishList: MutableState<ArrayList<String>> =
        mutableStateOf(
            arrayListOf("")
    )
    private val allUnVerifiedNotes = MutableStateFlow<List<Note>>(emptyList())

    init {
        getAllEnglishWords()
        getAllUnverifiedNotes()
    }

    private fun getAllEnglishWords() = CoroutineScope(Dispatchers.IO).launch {
        englishList.value = repo.getAllEnglishWords()
    }

    private fun getAllUnverifiedNotes() = CoroutineScope(Dispatchers.IO).launch {
        getAllUnVerifiedNotes.invoke().collect { list ->
            allUnVerifiedNotes.value = list.map { note ->
                noteMapper.toView(note)
            }
        }
    }

    override suspend fun doWork(): Result {
        return try {
            englishList.value.forEach { word ->
                allUnVerifiedNotes.value.forEach { note ->
                    if(note.dataEntity.title
                            ?.lowercase()
                            ?.split(' ')
                            ?.contains(word) == true) {
                        editData.invoke(
                            dataMapper.toDomain(
                                note.dataEntity.copy(
                                    title= note.dataEntity.title
                                        ?.split(' ')?.joinToString(" ") {
                                            if (it.lowercase() == word) {
                                                "\uD83E\uDD2C"
//                                                ""
                                            } else
                                                it
                                        }
                                )
                            )
                        )
                    }
                }
            }

            Result.success()
        } catch (e: Exception) {
            Result.success()
        }
    }
}