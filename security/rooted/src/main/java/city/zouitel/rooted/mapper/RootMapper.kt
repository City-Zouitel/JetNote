package city.zouitel.rooted.mapper

import city.zouitel.domain.model.Root as OutRoot
import city.zouitel.rooted.model.Root as InRoot
import city.zouitel.rooted.mapper.base.Mapper

class RootMapper: Mapper.ReadOnly<OutRoot, InRoot> {
    override fun toView(data: OutRoot): InRoot = with(data) {
        InRoot(isDeviceRooted)
    }
}