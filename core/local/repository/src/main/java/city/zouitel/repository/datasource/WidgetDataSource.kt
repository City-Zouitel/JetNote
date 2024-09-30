package city.zouitel.repository.datasource

import city.zouitel.repository.model.Note
import kotlinx.coroutines.flow.Flow

interface WidgetDataSource {

    val getAllWidgetMainEntityById: Flow<List<Note>>
}