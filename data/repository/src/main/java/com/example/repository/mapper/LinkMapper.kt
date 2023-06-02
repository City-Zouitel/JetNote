package com.example.repository.mapper

import com.example.domain.model.Link as OutLink
import com.example.repository.mapper.base.Mapper
import com.example.repository.model.Link as InLink

class LinkMapper: Mapper.Base<InLink, OutLink> {
    override fun toRepository(data: OutLink): InLink = with(data) {
        InLink(id, url, host, image, title, description)
    }

    override fun toDomain(data: InLink): OutLink = with(data) {
        OutLink(id, url, host, image, title, description)
    }
}