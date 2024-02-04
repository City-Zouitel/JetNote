package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.WidgetDao
import city.zouitel.database.mapper.WidgetMapper
import city.zouitel.repository.datasource.WidgetDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Note as OutNote

class WidgetDataSourceImpl /*@Inject*/ constructor(
    private val widgetDao: WidgetDao,
    private val widgetMapper: WidgetMapper
): WidgetDataSource {
    override val getAllWidgetMainEntityById: Flow<List<OutNote>>
        get() = widgetDao.allWidgetEntitiesById().map { list ->
            list.map { note ->
                widgetMapper.readOnly(note)
            }
        }
}