package com.example.repository.mapper

import com.example.repository.mapper.base.Mapper
import com.example.repository.model.Tag as InTag
import com.example.domain.model.Tag as OutTag

class TagMapper: Mapper.Base<InTag, OutTag> {
    override fun toRepository(data: OutTag): InTag = with(data){
        InTag(id, label, color)
    }

    override fun readOnly(data: InTag): OutTag = with(data){
        OutTag(id, label, color)
    }
}