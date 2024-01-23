package city.zouitel.note.mapper

import city.zouitel.tasks.mapper.base.Mapper
import city.zouitel.note.model.Data as InData
import city.zouitel.domain.model.Data as OutData

class DataMapper: Mapper.Base<InData, OutData> {
    override fun toView(data: OutData): InData = with(data) {
        InData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, imageUrl, audioUrl)
    }

    override fun toDomain(data: InData): OutData = with(data) {
        OutData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, imageUrl, audioUrl)
    }
}