package city.zouitel.database.mapper

import city.zouitel.database.model.LinkEntity as InLink
import city.zouitel.repository.model.Link as OutLink

class LinkMapper {

    fun toRepo(links: List<InLink>) = links.map { toRepo(it) }

    fun toRepo(link: InLink) = OutLink(
        id = link.id,
        url = link.url,
        host = link.host,
        image = link.image,
        title = link.title,
        description = link.description
    )

    fun fromRepo(link: OutLink) = InLink(
        id = link.id,
        url = link.url,
        host = link.host,
        image = link.image,
        title = link.title,
        description = link.description
    )
}