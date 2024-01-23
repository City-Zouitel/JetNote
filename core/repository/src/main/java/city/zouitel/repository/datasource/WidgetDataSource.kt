package city.zouitel.repository.datasource

import city.zouitel.repository.model.Note

interface WidgetDataSource {

    val getAllWidgetMainEntityById: List<Note>
}