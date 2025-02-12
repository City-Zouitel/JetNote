package city.zouitel.audio.mapper

import city.zouitel.audio.model.Record
import city.zouitel.domain.model.Record as OutRecord

class RecordMapper {
    fun fromDomain(records: List<OutRecord>) = records.map { fromDomain(it) }

    private fun fromDomain(record: OutRecord) = Record(
        id = record.id,
        uid = record.uid,
        title = record.title,
        path = record.path,
        duration = record.duration
    )
}