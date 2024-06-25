package city.zouitel.security.mapper

import city.zouitel.repository.model.Root as OutRoot
import city.zouitel.security.model.Root as InRoot
import city.zouitel.security.mapper.base.Mapper
import city.zouitel.security.model.Root

class RootMapper {

    fun toRepo(root: InRoot) = OutRoot(isDeviceRooted = root.isDeviceRooted)
}