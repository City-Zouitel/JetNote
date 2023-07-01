package com.example.quick_note.mapper

import com.example.domain.model.Data as OutData
import com.example.quick_note.mapper.base.Mapper
import com.example.quick_note.model.QuickData as InData

class QuickDataMapper: Mapper.Base<InData, OutData> {
    override fun toView(data: OutData): InData = with(data) {
        InData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, isVerified)
    }

    override fun toDomain(data: InData): OutData = with(data) {
        OutData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, isVerified)
    }
}