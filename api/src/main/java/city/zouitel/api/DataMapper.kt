package city.zouitel.api

import com.example.domain.model.Data as OutData
import city.zouitel.api.Data as InData

class DataMapper: Mapper.Base<InData, OutData> {
    override fun toView(data: OutData): InData = with(data) {
        InData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, isVerified)
    }

    override fun toDomain(data: InData): OutData = with(data) {
        OutData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, isVerified)
    }
}