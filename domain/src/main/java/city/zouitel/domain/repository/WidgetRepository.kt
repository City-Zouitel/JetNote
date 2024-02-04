package city.zouitel.domain.repository

import kotlinx.coroutines.flow.Flow
import city.zouitel.domain.model.Note as OutNote
interface WidgetRepository {

    val getAllWidgetMainEntityById: Flow<List<OutNote>>
}