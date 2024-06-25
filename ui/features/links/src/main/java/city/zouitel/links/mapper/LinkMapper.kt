package city.zouitel.links.mapper

import city.zouitel.domain.model.Link as OutLink
import city.zouitel.links.mapper.base.Mapper
import city.zouitel.links.model.Link as InLink

class LinkMapper {

    fun fromDomain(links: List<OutLink>) = links.map { fromDomain(it) }

    fun toDomain(link: InLink) = OutLink(
        id = link.id,
        url = link.url,
        host = link.host,
        image = link.image,
        title = link.title,
        description = link.description
    )

    fun fromDomain(link: OutLink) = InLink(
        id = link.id,
        url = link.url,
        host = link.host,
        image = link.image,
        title = link.title,
        description = link.description
    )
}