package city.zouitel.recoder.mapper

import city.zouitel.recoder.model.Record
import city.zouitel.audio.model.Record as AudioRecord
import city.zouitel.domain.model.Record as OutRecord

class RecordMapper {
    fun fromDomain(records: List<OutRecord>) = records.map { fromDomain(it) }

    fun toDomain(record: Record) = OutRecord(
        id = record.id,
        uid = record.uid,
        title = record.title,
        path = record.path,
        duration = record.duration
    )

    private fun fromDomain(record: OutRecord) = Record(
        id = record.id,
        uid = record.uid,
        title = record.title,
        path = record.path,
        duration = record.duration
    )

    fun fromAudio(record: Record) = AudioRecord (
        id = record.id,
        uid = record.uid,
        title = record.title,
        path = record.path,
        duration = record.duration
    )
}