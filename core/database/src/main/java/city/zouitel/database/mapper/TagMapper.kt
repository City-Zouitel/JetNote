package city.zouitel.database.mapper

import city.zouitel.database.mapper.base.Mapper
import city.zouitel.database.model.TagEntity as InTag
import city.zouitel.repository.model.Tag as OutTag

class TagMapper: Mapper.Base<InTag, OutTag> {
    override fun toLocal(data: OutTag): InTag = with(data){
        InTag(id, label, color)
    }

    override fun readOnly(data: InTag): OutTag = with(data){
        OutTag(id, label, color)
    }
}