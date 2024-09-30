package city.zouitel.repository.datasource

import kotlinx.coroutines.flow.Flow
import city.zouitel.repository.model.Root as OutRoot


interface RootDataSource {

    val isDeviceRooted: Flow<Result<OutRoot>>

}
