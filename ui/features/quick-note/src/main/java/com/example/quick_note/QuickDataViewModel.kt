package com.example.quick_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.DataUseCase
import com.example.quick_note.mapper.QuickDataMapper
import com.example.quick_note.model.QuickData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuickDataViewModel @Inject constructor(
    private val add: DataUseCase.AddData,
    private val mapper: QuickDataMapper
) : ViewModel() {
    fun addQuickData(data: QuickData) = viewModelScope.launch(Dispatchers.IO) {
        add.invoke(mapper.toDomain(data))
    }
}