package city.zouitel.database.mapper

import city.zouitel.database.model.Record
import city.zouitel.repository.model.Record as OutRecord

class RecordMapper {
    fun toRepo(records: List<Record>) = records.map { toRepo(it) }

    fun toRepo(record: Record) = OutRecord(
        id = record.id,
        uid = record.uid,
        title = record.title,
        path = record.path,
        duration = record.duration
    )

    fun fromRepo(record: OutRecord) = Record(
        id = record.id,
        uid = record.uid,
        title = record.title,
        path = record.path,
        duration = record.duration
    )
}