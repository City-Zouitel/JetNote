package city.zouitel.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.zouitel.domain.usecase.RootUseCase
import city.zouitel.root.mapper.RootMapper
import city.zouitel.root.model.Root as InRoot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RootViewModel(
    rootUseCase: RootUseCase.RootUseCase,
    private val mapper: RootMapper
): ViewModel() {

    private val _isDeviceRooted = MutableStateFlow(runCatching { InRoot(false) })

    val isDeviceRooted: StateFlow<Result<InRoot>> =
        _isDeviceRooted.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            runCatching { InRoot(false) }
        )


    init {
        viewModelScope.launch(Dispatchers.IO) {
            rootUseCase.invoke().collect { result ->
                _isDeviceRooted.value = result.map { mapper.toView(it) }
            }
        }
    }
}