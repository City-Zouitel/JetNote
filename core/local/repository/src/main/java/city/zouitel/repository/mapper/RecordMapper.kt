package city.zouitel.repository.mapper

import city.zouitel.repository.model.Record
import city.zouitel.domain.model.Record as OutRecord

class RecordMapper {
    fun toDomain(records: List<Record>) = records.map { toDomain(it) }

    fun toDomain(record: Record) = OutRecord(
        id = record.id,
        uid = record.uid,
        title = record.title,
        path = record.path,
        duration = record.duration
    )

    fun fromDomain(record: OutRecord) = Record(
        id = record.id,
        uid = record.uid,
        title = record.title,
        path = record.path,
        duration = record.duration
    )
}