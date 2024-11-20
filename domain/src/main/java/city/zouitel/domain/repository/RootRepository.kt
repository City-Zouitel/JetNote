package city.zouitel.domain.repository

import kotlinx.coroutines.flow.Flow
import city.zouitel.domain.model.Root as OutRoot

interface RootRepository {

    val isDeviceRooted: Flow<Result<OutRoot>>
}
