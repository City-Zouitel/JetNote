package city.zouitel.repository.mapper

import city.zouitel.repository.mapper.base.Mapper
import city.zouitel.repository.model.Tag as InTag
import city.zouitel.domain.model.Tag as OutTag

class TagMapper: Mapper.Base<InTag, OutTag> {
    override fun toRepository(data: OutTag): InTag = with(data){
        InTag(id, label, color)
    }

    override fun toDomain(data: InTag): OutTag = with(data){
        OutTag(id, label, color)
    }
}