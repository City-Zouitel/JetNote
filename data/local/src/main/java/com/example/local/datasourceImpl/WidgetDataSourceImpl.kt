package com.example.local.datasourceImpl

import com.example.local.dao.WidgetDao
import com.example.local.mapper.WidgetMapper
import com.example.repository.datasource.WidgetDataSource
import com.example.repository.model.Note
import javax.inject.Inject

class WidgetDataSourceImpl @Inject constructor(
    private val widgetDao: WidgetDao,
    private val widgetMapper: WidgetMapper
): WidgetDataSource {
    override val getAllWidgetMainEntityById: List<Note>
        get() = TODO("Not yet implemented")
}