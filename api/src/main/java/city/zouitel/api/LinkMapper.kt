package city.zouitel.api

import city.zouitel.api.Link as InLink
import com.example.domain.model.Link as OutLink

class LinkMapper: Mapper.Base<InLink, OutLink> {
    override fun toView(data: OutLink): InLink = with(data) {
        InLink(id, url, host, image, title, description)
    }

    override fun toDomain(data: InLink): OutLink = with(data) {
        OutLink(id, url, host, image, title, description)
    }
}