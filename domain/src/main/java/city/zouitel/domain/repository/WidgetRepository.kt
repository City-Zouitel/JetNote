package city.zouitel.domain.repository

import city.zouitel.domain.model.Note as OutNote


interface WidgetRepository {

    val getAllWidgetMainEntityById: List<OutNote>
}