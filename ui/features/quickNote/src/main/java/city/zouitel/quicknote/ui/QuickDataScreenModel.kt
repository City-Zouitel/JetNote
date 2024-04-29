package city.zouitel.quicknote.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.DataUseCase
import city.zouitel.quicknote.mapper.QuickDataMapper
import city.zouitel.quicknote.model.QuickData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuickDataScreenModel(
    private val add: DataUseCase.AddData,
    private val mapper: QuickDataMapper
): ScreenModel {

    fun addQuickData(data: QuickData) = screenModelScope.launch(Dispatchers.IO) {
        add.invoke(mapper.toDomain(data))
    }
}