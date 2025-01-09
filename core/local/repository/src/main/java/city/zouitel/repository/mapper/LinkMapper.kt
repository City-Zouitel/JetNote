package city.zouitel.repository.mapper

import city.zouitel.repository.model.Link
import city.zouitel.domain.model.Link as OutLink

class LinkMapper {

    fun toDomain(links: List<Link>) = links.map { toDomain(it) }

    fun toDomain(link: Link) = OutLink(
        id = link.id,
        uid = link.uid,
        url = link.url,
        title = link.title,
        description = link.description,
        image = link.image,
        icon = link.icon
    )

    fun fromDomain(link: OutLink) = Link(
        id = link.id,
        uid = link.uid,
        url = link.url,
        title = link.title,
        description = link.description,
        image = link.image,
        icon = link.icon
    )
}