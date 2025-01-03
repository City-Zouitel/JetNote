package city.zouitel.quicknote.mapper

import city.zouitel.domain.model.Data as OutData
import city.zouitel.quicknote.model.QuickData as InData

class QuickDataMapper {

    fun toDomain(data: InData) = OutData(
        uid = data.uid,
        title = data.title,
        description = data.description,
        priority = data.priority,
        color = data.color,
        textColor = data.textColor,
        date = data.date,
        removed = data.removed
    )
}