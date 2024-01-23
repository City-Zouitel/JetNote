package city.zouitel.tags.mapper

import city.zouitel.domain.model.Tag as OutTag
import city.zouitel.tags.mapper.base.Mapper
import city.zouitel.tags.model.Tag as InTag

class TagMapper: Mapper.Base<InTag, OutTag> {
    override fun toView(data: OutTag): InTag = with(data) {
        InTag(id, label, color)
    }

    override fun toDomain(data: InTag): OutTag = with(data){
        OutTag(id, label, color)
    }
}