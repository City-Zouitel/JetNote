package com.example.common_ui

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
            Cons.GRID
        )

    val getSound: StateFlow<Boolean> = dataStoreRepo.getSound
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            false
        )

    val getOrdination: StateFlow<String> = dataStoreRepo.getOrdination
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Cons.BY_NAME
        )

    val getTheme: StateFlow<String> = dataStoreRepo.getTheme
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            com.example.datastore.Cons.DARK
        )



    fun setSound(sound: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setSound(sound)
        }
    }
    fun setOrdination(order: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setOrdination(order)
        }
    }

    fun setLayout(layout: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setLayout(layout)
        }
    }

    fun setTheme(theme: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.setTheme(theme)
        }
    }
}