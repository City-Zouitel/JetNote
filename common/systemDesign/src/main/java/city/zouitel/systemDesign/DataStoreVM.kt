package city.zouitel.systemDesign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.zouitel.datastore.Cons
import city.zouitel.datastore.DataStoreRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DataStoreVM(
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    val getLayout: StateFlow<String> = dataStoreRepo.getLayout.filter {
        it.isNotEmpty()
    }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Cons.GRID
        )

    val getSound: StateFlow<Boolean> = dataStoreRepo.getSound
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            false
        )

    val getOrdination: StateFlow<String> = dataStoreRepo.getOrdination
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            city.zouitel.systemDesign.Cons.BY_NAME
        )

    val getTheme: StateFlow<String> = dataStoreRepo.getTheme
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Cons.DARK
        )

    fun setSound(sound: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setSound(sound)
        }
    }
    fun setOrdination(order: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setOrdination(order)
        }
    }

    fun setLayout(layout: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setLayout(layout)
        }
    }

    fun setTheme(theme: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setTheme(theme)
        }
    }
}