package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.LinkEntity as InLink
import com.example.repository.model.Link as OutLink

class LinkMapper : Mapper.Base<InLink, OutLink> {
    override fun toLocal(data: OutLink): InLink = with(data) {
        InLink(id, url, host, image, title, description)
    }

    override fun readOnly(data: InLink): OutLink = with(data) {
        OutLink(id, url, host, image, title, description)
    }
}