package com.example.repository.mapper

import com.example.repository.mapper.base.Mapper
import com.example.repository.model.Data as InData
import com.example.domain.model.Data as OutData

class DataMapper: Mapper.Base<InData, OutData> {
    override fun toRepository(data: OutData): InData = with(data){
        InData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, imageUrl, audioUrl)
    }

    override fun readOnly(data: InData): OutData = with(data){
        OutData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, imageUrl, audioUrl)
    }
}