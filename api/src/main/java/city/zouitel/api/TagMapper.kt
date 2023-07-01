package city.zouitel.api

import com.example.domain.model.Tag as OutTag
import city.zouitel.api.Tag as InTag

class TagMapper: Mapper.Base<InTag, OutTag> {
    override fun toView(data: OutTag): InTag = with(data) {
        InTag(id, label, color)
    }

    override fun toDomain(data: InTag): OutTag = with(data){
        OutTag(id, label, color)
    }
}