package city.zouitel.repository.mapper

import city.zouitel.repository.model.Data as InData
import city.zouitel.domain.model.Data as OutData

class DataMapper {

    fun toDomain(data: InData) = OutData(
        uid = data.uid,
        title = data.title,
        description = data.description,
        priority = data.priority,
        color = data.color,
        textColor = data.textColor,
        date = data.date,
        removed = data.removed,
        reminding = data.reminding
    )

    fun fromDomain(data: OutData) = InData(
        uid = data.uid,
        title = data.title,
        description = data.description,
        priority = data.priority,
        color = data.color,
        textColor = data.textColor,
        date = data.date,
        removed = data.removed,
        reminding = data.reminding
    )
}