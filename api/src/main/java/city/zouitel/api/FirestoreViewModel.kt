package city.zouitel.api

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    application: Application,
    private val repo: FirestoreRepoImp
    ): ViewModel() {

    var isLoading by mutableStateOf(false)
    private set

    val englishList : MutableState<DataOrException<List<Data>, Exception>>
            = mutableStateOf(
        DataOrException(listOf(),Exception())
    )

    private var workManager = WorkManager.getInstance(application)

    init {
        getAllEnglishWords()
    }

    private fun getAllEnglishWords() = viewModelScope.launch {
            isLoading = true
            englishList.value = repo.getAllEnglishWords()
            isLoading = false
        }


    fun addDataToCloud(data: Data) {
        viewModelScope.launch {
            isLoading = true
            repo.addData(data)
            isLoading = false
        }
    }


    fun doWork(
        title: String?,
        description: String?
    ) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val worker = OneTimeWorkRequest.Builder(Worker::class.java)
            .addTag("api_work")
            .setInputData(
                androidx.work.Data.Builder()
                    .putString("title_data", title)
                    .putString("description_data", description)
                    .build()
            )
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            "unique_api_worker",
            ExistingWorkPolicy.KEEP,
            worker
        )
    }

}
