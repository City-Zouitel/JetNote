package city.zouitel.links.mapper

import city.zouitel.links.model.Link
import city.zouitel.domain.model.Link as OutLink

class LinkMapper {

    fun fromDomain(links: List<OutLink>) = links.map { fromDomain(it) }

    fun toDomain(link: Link) = OutLink(
        id = link.id,
        uid = link.uid,
        url = link.url,
        title = link.title,
        description = link.description,
        image = link.image,
        icon = link.icon
    )

    private fun fromDomain(link: OutLink) = Link(
        id = link.id,
        uid = link.uid,
        url = link.url,
        title = link.title,
        description = link.description,
        image = link.image,
        icon = link.icon
    )
}