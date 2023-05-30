package com.example.repository.mapper

import com.example.domain.model.Link as O
import com.example.repository.mapper.base.MapperBase
import com.example.repository.model.Link as I

class LinkMapper: MapperBase<O, I> {
    override fun toRepository(data: O): I = with(data) {
        I(id, url, host, image, title, description)
    }

    override fun toDomain(data: I): O = with(data) {
        O(id, url, host, image, title, description)
    }
}