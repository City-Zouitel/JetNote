package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.DataEntity as InData
import com.example.repository.model.Data as OutData

class DataMapper: Mapper.Base<InData, OutData> {
    override fun toLocal(data: OutData): InData = with(data){
        InData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, imageUrl, audioUrl)
    }

    override fun readOnly(data: InData): OutData = with(data){
        OutData(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, imageUrl, audioUrl)
    }
}