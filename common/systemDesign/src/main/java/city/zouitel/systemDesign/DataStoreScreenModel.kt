package city.zouitel.systemDesign

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.datastore.DataStoreRepo
import city.zouitel.systemDesign.CommonConstants.DARK
import city.zouitel.systemDesign.CommonConstants.GRID
import city.zouitel.systemDesign.CommonConstants.NAME_ORDER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DataStoreScreenModel(
    private val dataStoreRepo: DataStoreRepo
): ScreenModel {

    val getLayout: StateFlow<String> = dataStoreRepo.getLayout.filter {
        it.isNotEmpty()
    }.stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            GRID
        )

    val getSound: StateFlow<Boolean> = dataStoreRepo.getSound
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            false
        )

    val getOrdination: StateFlow<String> = dataStoreRepo.getOrdination
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            NAME_ORDER
        )

    val getTheme: StateFlow<String> = dataStoreRepo.getTheme
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            DARK
        )

    fun setSound(sound: Boolean) {
        screenModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setSound(sound)
        }
    }
    fun setOrdination(order: String) {
        screenModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setOrdination(order)
        }
    }

    fun setLayout(layout: String) {
        screenModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setLayout(layout)
        }
    }

    fun setTheme(theme: String) {
        screenModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setTheme(theme)
        }
    }
}