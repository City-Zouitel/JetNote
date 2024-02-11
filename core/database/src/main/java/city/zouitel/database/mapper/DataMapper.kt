package city.zouitel.database.mapper

import city.zouitel.database.mapper.base.Mapper
import city.zouitel.database.model.DataEntity as InData
import city.zouitel.repository.model.Data as OutData

class DataMapper: Mapper.Base<InData, OutData> {
    override fun toLocal(data: OutData): InData = with(data){
        InData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, imageUrl, audioUrl)
    }

    override fun readOnly(data: InData): OutData = with(data){
        OutData(uid, title, description, priority, color, textColor, date,
            removed, audioDuration, reminding, imageUrl, audioUrl)
    }
}