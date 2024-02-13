package city.zouitel.quicknote.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.zouitel.domain.usecase.DataUseCase
import city.zouitel.quicknote.mapper.QuickDataMapper
import city.zouitel.quicknote.model.QuickData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuickDataViewModel(
    private val add: DataUseCase.AddData,
    private val mapper: QuickDataMapper
): ViewModel() {

    fun addQuickData(data: QuickData) = viewModelScope.launch(Dispatchers.IO) {
        add.invoke(mapper.toDomain(data))
    }
}