package com.example.glance

import com.example.local.daos.EntityDao
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class Repo @Inject constructor(
    private val dao: EntityDao
) {

    fun getAllLocalNotes() = dao.getAllNotesForWidget()
}