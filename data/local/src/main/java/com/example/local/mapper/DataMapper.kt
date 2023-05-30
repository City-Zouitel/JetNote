package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.DataEntity
import com.example.repository.model.Data

class DataMapper: Mapper.Base<DataEntity, Data> {
    override fun toLocal(data: Data): DataEntity = with(data){
        DataEntity(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, imageUrl, audioUrl)
    }

    override fun readOnly(data: DataEntity): Data = with(data){
        Data(uid, title, description, priority, color, textColor, date,
            trashed, audioDuration, reminding, imageUrl, audioUrl)
    }
}