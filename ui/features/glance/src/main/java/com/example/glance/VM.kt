package com.example.glance

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.reposImpl.EntityRepoImp
import com.example.local.model.Entity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class VM @Inject constructor(
    private val repo: Repo
) {

    @WorkerThread
    fun getAllNotes() = repo.getAllLocalNotes()
}