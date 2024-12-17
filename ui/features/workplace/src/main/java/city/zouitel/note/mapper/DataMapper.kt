package city.zouitel.note.mapper

import city.zouitel.domain.model.Data as OutData
import city.zouitel.note.model.Data as InData

class DataMapper {

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

    fun fromDomain(data: OutData) = InData(
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