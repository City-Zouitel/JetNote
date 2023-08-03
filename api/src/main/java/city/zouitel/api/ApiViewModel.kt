package city.zouitel.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val repo: ApiRepositoryImpl
): ViewModel() {

    private val _uiState = MutableStateFlow<ApiResult<*>>(ApiResult.Loading)
    val uiState: StateFlow<ApiResult<*>>
        get() = _uiState
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                ApiResult.Loading
            )

    init {
        getAllData()
    }

    private fun getAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllData().collect {
                _uiState.value = it
            }
        }
    }

    fun dataModifier(data: Invalid) {
        viewModelScope.launch {
            repo.dataModifier(data).collectLatest {
                _uiState.update { it }
            }
        }
    }
}
