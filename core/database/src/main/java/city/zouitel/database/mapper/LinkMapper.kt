package city.zouitel.database.mapper

import city.zouitel.database.mapper.base.Mapper
import city.zouitel.database.model.LinkEntity as InLink
import city.zouitel.repository.model.Link as OutLink

class LinkMapper : Mapper.Base<InLink, OutLink> {
    override fun toLocal(data: OutLink): InLink = with(data) {
        InLink(id, url, host, image, title, description)
    }

    override fun readOnly(data: InLink): OutLink = with(data) {
        OutLink(id, url, host, image, title, description)
    }
}