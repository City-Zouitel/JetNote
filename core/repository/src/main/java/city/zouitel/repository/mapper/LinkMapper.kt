package city.zouitel.repository.mapper

import city.zouitel.domain.model.Link as OutLink
import city.zouitel.repository.model.Link as InLink

class LinkMapper {

    fun toDomain(links: List<InLink>) = links.map { toDomain(it) }

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