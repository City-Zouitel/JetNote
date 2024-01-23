package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.WidgetDao
import city.zouitel.database.mapper.WidgetMapper
import city.zouitel.repository.datasource.WidgetDataSource
import city.zouitel.repository.model.Note as OutNote

class WidgetDataSourceImpl /*@Inject*/ constructor(
    private val widgetDao: WidgetDao,
    private val widgetMapper: WidgetMapper
): WidgetDataSource {
    override val getAllWidgetMainEntityById: List<OutNote>
        get() = widgetDao.allWidgetEntitiesById().map {
            widgetMapper.readOnly(it)
        }
}