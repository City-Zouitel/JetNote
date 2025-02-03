package city.zouitel.note.mapper

import city.zouitel.note.model.Data
import city.zouitel.domain.model.Data as OutData

class DataMapper {

    fun toDomain(data: Data) = OutData(
        uid = data.uid,
        title = data.title,
        description = data.description,
        priority = data.priority,
        background = data.background,
        textColor = data.textColor,
        date = data.date,
        archived = data.archived
    )

    fun fromDomain(data: OutData) = Data(
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