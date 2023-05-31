package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.TagEntity as InTag
import com.example.repository.model.Tag as OutTag

class TagMapper: Mapper.Base<InTag, OutTag> {
    override fun toLocal(data: OutTag): InTag = with(data){
        InTag(id, label, color)
    }

    override fun readOnly(data: InTag): OutTag = with(data){
        OutTag(id, label, color)
    }
}