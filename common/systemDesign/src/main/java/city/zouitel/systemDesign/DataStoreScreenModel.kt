package city.zouitel.systemDesign

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.datastore.DataStoreRepo
import city.zouitel.systemDesign.CommonConstants.DARK
import city.zouitel.systemDesign.CommonConstants.DEFAULT_ORDER
import city.zouitel.systemDesign.CommonConstants.GRID
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

    val isMute: StateFlow<Boolean> = dataStoreRepo.isMute
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            true
        )

    val getOrdination: StateFlow<String> = dataStoreRepo.getOrdination
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            DEFAULT_ORDER
        )

    val getTheme: StateFlow<String> = dataStoreRepo.getTheme
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            DARK
        )

    val isLockMode: StateFlow<Boolean> = dataStoreRepo.isLockMode
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            false
        )

    val getScreenshotBlock: StateFlow<Boolean> = dataStoreRepo.isScreenshotBlock
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            false
        )

    val getGeminiApiKey: StateFlow<String> = dataStoreRepo.getGeminiApiKey
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            CommonConstants.EMPTY_API_KEY
        )

    fun setMute(isMute: Boolean) {
        screenModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setMute(isMute)
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

    fun setLockMode(isLocked: Boolean) {
        screenModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setLockMode(isLocked)
        }
    }

    fun setScreenshotBlock(isBlocked: Boolean) {
        screenModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setScreenshotBlock(isBlocked)
        }
    }

    fun setGeminiApiKey(apiKey: String) {
        screenModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setGeminiApiKey(apiKey)
        }
    }
}