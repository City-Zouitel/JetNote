package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.LinkEntity
import com.example.repository.model.Link

class LinkMapper : Mapper.Base<LinkEntity, Link> {
    override fun toLocal(data: Link): LinkEntity = with(data) {
        LinkEntity(id, url, host, image, title, description)
    }

    override fun readOnly(data: LinkEntity): Link = with(data) {
        Link(id, url, host, image, title, description)
    }
}