package city.zouitel.database.mapper

import city.zouitel.database.model.DataEntity as InData
import city.zouitel.repository.model.Data as OutData

class DataMapper {

    fun toRepo(data: InData) = OutData(
        uid = data.uid,
        title = data.title,
        description = data.description,
        priority = data.priority,
        color = data.color,
        textColor = data.textColor,
        date = data.date,
        removed = data.removed
    )

    fun fromRepo(data: OutData) = InData(
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