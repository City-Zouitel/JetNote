package com.example.note.mapper

import com.example.tasks.mapper.base.Mapper
import com.example.note.model.Data as InData
import com.example.domain.model.Data as OutData

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