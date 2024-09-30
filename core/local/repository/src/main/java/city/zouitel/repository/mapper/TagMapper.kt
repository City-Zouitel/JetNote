package city.zouitel.repository.mapper

import city.zouitel.repository.model.Tag as InTag
import city.zouitel.domain.model.Tag as OutTag

class TagMapper {

    fun toDomain(tags: List<InTag>) = tags.map { toDomain(it) }

    fun toDomain(tag: InTag) = OutTag(
        id = tag.id,
        label = tag.label,
        color = tag.color
    )

    fun fromDomain(tag: OutTag) = InTag(
        id = tag.id,
        label = tag.label,
        color = tag.color
    )
}