package com.example.links.mapper

import com.example.domain.model.Link as OutLink
import com.example.links.mapper.base.Mapper
import com.example.links.model.Link as InLink

class LinkMapper: Mapper.Base<InLink, OutLink> {
    override fun toView(data: OutLink): InLink = with(data) {
        InLink(id, url, host, image, title, description)
    }

    override fun toDomain(data: InLink): OutLink = with(data) {
        OutLink(id, url, host, image, title, description)
    }
}