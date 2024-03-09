package city.zouitel.repository.mapper

import city.zouitel.domain.model.Root as OutRoot
import city.zouitel.repository.mapper.base.Mapper
import city.zouitel.repository.model.Root
import city.zouitel.repository.model.Root as InRoot

class RootMapper: Mapper.ReadOnly<InRoot, OutRoot> {
    override fun toDomain(data: InRoot): OutRoot = with(data) {
        OutRoot(isDeviceRooted)
    }
}