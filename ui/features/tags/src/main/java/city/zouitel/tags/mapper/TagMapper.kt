package city.zouitel.tags.mapper

import city.zouitel.tags.model.Tag
import city.zouitel.domain.model.Tag as OutTag

class TagMapper {

    fun fromDomain(tags: List<OutTag>) = tags.map { fromDomain(it) }

    fun toDomain(tag: Tag) = OutTag(
        id = tag.id,
        label = tag.label,
        color = tag.color
    )

    fun fromDomain(tag: OutTag) = Tag(
        id = tag.id,
        label = tag.label,
        color = tag.color
    )
}