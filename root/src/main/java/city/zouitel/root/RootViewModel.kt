package city.zouitel.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RootViewModel(
    private val isRooted: RootUseCase
): ViewModel() {

    private val _isDeviceRooted = MutableStateFlow(false)

    val isDeviceRooted : StateFlow<Boolean> = _isDeviceRooted.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        false
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isDeviceRooted.value = isRooted.invoke()
        }
    }
}