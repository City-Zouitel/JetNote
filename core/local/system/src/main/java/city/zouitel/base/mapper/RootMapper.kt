package city.zouitel.base.mapper

import city.zouitel.base.model.Root as InRoot
import city.zouitel.repository.model.Root as OutRoot

class RootMapper {

    fun toRepo(root: InRoot) = OutRoot(isDeviceRooted = root.isDeviceRooted)
}