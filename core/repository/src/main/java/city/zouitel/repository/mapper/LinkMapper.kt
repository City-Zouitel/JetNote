package city.zouitel.repository.mapper

import city.zouitel.domain.model.Link as OutLink
import city.zouitel.repository.mapper.base.Mapper
import city.zouitel.repository.model.Link as InLink

class LinkMapper: Mapper.Base<InLink, OutLink> {
    override fun toRepository(data: OutLink): InLink = with(data) {
        InLink(id, url, host, image, title, description)
    }

    override fun toDomain(data: InLink): OutLink = with(data) {
        OutLink(id, url, host, image, title, description)
    }
}