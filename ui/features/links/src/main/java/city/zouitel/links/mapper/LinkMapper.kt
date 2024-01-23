package city.zouitel.links.mapper

import city.zouitel.domain.model.Link as OutLink
import city.zouitel.links.mapper.base.Mapper
import city.zouitel.links.model.Link as InLink

class LinkMapper: Mapper.Base<InLink, OutLink> {
    override fun toView(data: OutLink): InLink = with(data) {
        InLink(id, url, host, image, title, description)
    }

    override fun toDomain(data: InLink): OutLink = with(data) {
        OutLink(id, url, host, image, title, description)
    }
}