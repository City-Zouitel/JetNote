package city.zouitel.database.mapper

import city.zouitel.database.model.Link
import city.zouitel.repository.model.Link as OutLink

class LinkMapper {

    fun toRepo(links: List<Link>) = links.map { toRepo(it) }

    fun toRepo(link: Link) = OutLink(
        id = link.id,
        uid = link.uid,
        url = link.url,
        title = link.title,
        description = link.description,
        image = link.image,
        icon = link.icon
    )

    fun fromRepo(link: OutLink) = Link(
        id = link.id,
        uid = link.uid,
        url = link.url,
        title = link.title,
        description = link.description,
        image = link.image,
        icon = link.icon
    )
}