package city.zouitel.api

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    application: Application,
    private val repo: FirestoreRepoImp
    ): ViewModel() {

    var isProcessing by mutableStateOf(false)
        private set

//    val englishList: MutableState<DataOrException<List<Info>, Exception>> = mutableStateOf(
//        DataOrException(listOf(), Exception(""))
//    )

    private var workManager = WorkManager.getInstance(application)

//    init {
//        getAllEnglishWords()
//    }
//
//    private fun getAllEnglishWords() = viewModelScope.launch {
//        isProcessing = true
//        englishList.value = repo.getAllEnglishWords()
//        isProcessing = false
//    }


    fun addDataToCloud(info: Info) {
        viewModelScope.launch(Dispatchers.IO) {
            isProcessing = true
            repo.addData(info)
            isProcessing = false
        }
    }


    fun doWork(
        uid: String?,
        title: String?,
        description: String?
    ) = viewModelScope.launch(Dispatchers.IO) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val worker = OneTimeWorkRequest.Builder(Worker::class.java)
            .addTag("api_work")
            .setInputData(
                androidx.work.Data.Builder()
                    .putString("uid_data", uid)
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
