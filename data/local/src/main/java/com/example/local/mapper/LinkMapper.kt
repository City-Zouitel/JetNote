package com.example.local.mapper

import com.example.local.mapper.base.MapperBase
import com.example.repository.model.Link as O
import com.example.local.model.Link as I

class LinkMapper :MapperBase<O, I> {
    override fun toLocal(data: O): I = with(data) {
        I(id, url, host, image, title, description)
    }

    override fun toRepository(data: I): O = with(data) {
        O(id, url, host, image, title, description)
    }
}