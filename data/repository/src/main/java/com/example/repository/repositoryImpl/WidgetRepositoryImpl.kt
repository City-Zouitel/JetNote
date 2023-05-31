package com.example.repository.repositoryImpl

import com.example.domain.model.Note as OutNote
import com.example.domain.repository.WidgetRepository
import com.example.repository.datasource.WidgetDataSource
import com.example.repository.mapper.WidgetMapper
import javax.inject.Inject

class WidgetRepositoryImpl @Inject constructor(
    private val dataSource: WidgetDataSource,
    private val mapper: WidgetMapper
): WidgetRepository {
    override val getAllWidgetMainEntityById: List<OutNote>
        get() = dataSource.getAllWidgetMainEntityById.map { note ->
            mapper.readOnly(note)
        }
}