package city.zouitel.root.mapper

import city.zouitel.domain.model.Root as OutRoot
import city.zouitel.root.model.Root as InRoot
import city.zouitel.root.mapper.base.Mapper
import city.zouitel.root.model.Root

class RootMapper: Mapper.ReadOnly<OutRoot, InRoot> {
    override fun toView(data: OutRoot): InRoot = with(data) {
        InRoot(isDeviceRooted)
    }
}