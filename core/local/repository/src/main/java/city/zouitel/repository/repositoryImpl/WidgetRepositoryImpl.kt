package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Note as OutNote
import city.zouitel.domain.repository.WidgetRepository
import city.zouitel.repository.datasource.WidgetDataSource
import city.zouitel.repository.mapper.WidgetMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WidgetRepositoryImpl(
    private val dataSource: WidgetDataSource,
    private val mapper: WidgetMapper
): WidgetRepository {
    override val getAllWidgetMainEntityById: Flow<List<OutNote>>
        get() = dataSource.getAllWidgetMainEntityById.map { notes -> mapper.toDomain(notes) }
}