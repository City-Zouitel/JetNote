package com.example.tags.mapper

import com.example.domain.model.Tag as OutTag
import com.example.tags.mapper.base.Mapper
import com.example.tags.model.Tag as InTag

class TagMapper: Mapper.Base<InTag, OutTag> {
    override fun toView(data: OutTag): InTag = with(data) {
        InTag(id, label, color)
    }

    override fun toDomain(data: InTag): OutTag = with(data){
        OutTag(id, label, color)
    }
}