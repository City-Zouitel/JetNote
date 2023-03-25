package com.example.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {

    val getLayout: Flow<String>

    suspend fun setLayout(layout: String)
}