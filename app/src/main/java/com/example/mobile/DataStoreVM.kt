package com.example.mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastore.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreVM @Inject constructor(
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    val getLayout: StateFlow<String> = dataStoreRepo.getLayout.filter {
        it.isNotEmpty()
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            "GRID"
        )

    fun setLayout(layout: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setLayout(layout)
        }
    }
}