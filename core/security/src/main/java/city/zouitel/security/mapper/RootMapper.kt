package city.zouitel.security.mapper

import city.zouitel.repository.model.Root as OutRoot
import city.zouitel.security.model.Root as InRoot
import city.zouitel.security.mapper.base.Mapper

class RootMapper: Mapper.ReadOnly<InRoot, OutRoot> {

    override fun readOnly(data: InRoot): OutRoot = with(data) {
        OutRoot(isDeviceRooted)
    }
}