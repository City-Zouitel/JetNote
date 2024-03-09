package city.zouitel.domain.repository

import city.zouitel.domain.model.Root as OutRoot
import kotlinx.coroutines.flow.Flow

interface RootRepository {

    val isDeviceRooted: Flow<Result<OutRoot>>

}
