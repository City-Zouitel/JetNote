package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.TagEntity
import com.example.repository.model.Tag

class TagMapper: Mapper.Base<TagEntity, Tag> {
    override fun toLocal(data: Tag): TagEntity = with(data){
        TagEntity(id, label, color)
    }

    override fun readOnly(data: TagEntity): Tag = with(data){
        Tag(id, label, color)
    }
}