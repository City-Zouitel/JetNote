package city.zouitel.quicknote.mapper

import city.zouitel.domain.model.Data as OutData
import city.zouitel.quicknote.mapper.base.Mapper
import city.zouitel.quicknote.model.QuickData as InData

class QuickDataMapper: Mapper.Base<InData, OutData> {
    override fun toView(data: OutData): InData = with(data) {
        InData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, imageUrl, audioUrl)
    }

    override fun toDomain(data: InData): OutData = with(data) {
        OutData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, imageUrl, audioUrl)
    }
}