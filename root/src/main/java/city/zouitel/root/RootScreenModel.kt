package city.zouitel.root

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.RootUseCase
import city.zouitel.root.mapper.RootMapper
import city.zouitel.root.model.Root as InRoot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RootScreenModel(
    rootUseCase: RootUseCase.RootUseCase,
    private val mapper: RootMapper
): ScreenModel {

    private val _isDeviceRooted = MutableStateFlow(runCatching { InRoot(false) })

    val isDeviceRooted: StateFlow<Result<InRoot>> =
        _isDeviceRooted.stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            runCatching { InRoot(false) }
        )


    init {
        screenModelScope.launch(Dispatchers.IO) {
            rootUseCase.invoke().collect { result ->
                _isDeviceRooted.value = result.map { mapper.toView(it) }
            }
        }
    }
}