package city.zouitel.database.mapper

import city.zouitel.database.model.TagEntity as InTag
import city.zouitel.repository.model.Tag as OutTag

class TagMapper {

    fun toRepo(tags: List<InTag>) = tags.map { toRepo(it) }

    fun toRepo(tag: InTag) = OutTag(
        id = tag.id,
        label = tag.label,
        color = tag.color
    )

    fun fromRepo(tag: OutTag) = InTag(
        id = tag.id,
        label = tag.label,
        color = tag.color
    )
}