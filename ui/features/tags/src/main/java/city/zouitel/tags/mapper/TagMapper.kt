package city.zouitel.tags.mapper

import city.zouitel.domain.model.Tag as OutTag
import city.zouitel.tags.mapper.base.Mapper
import city.zouitel.tags.model.Tag as InTag

class TagMapper {

    fun fromDomain(tags: List<OutTag>) = tags.map { fromDomain(it) }

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