package com.example.repository.datasource

import com.example.repository.model.Note

interface WidgetDataSource {

    val getAllWidgetMainEntityById: List<Note>
}