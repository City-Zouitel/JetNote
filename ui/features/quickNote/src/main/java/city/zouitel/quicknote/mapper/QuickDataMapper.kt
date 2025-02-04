package city.zouitel.quicknote.mapper

import city.zouitel.quicknote.model.QuickData
import city.zouitel.domain.model.Data as OutData

class QuickDataMapper {

    fun toDomain(data: QuickData) = OutData(
        uid = data.uid,
        title = data.title,
        description = data.description,
        priority = data.priority,
        background = data.background,
        textColor = data.textColor,
        date = data.date,
        archived = data.archived
    )
}