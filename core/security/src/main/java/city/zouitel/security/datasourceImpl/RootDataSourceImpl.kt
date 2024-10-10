package city.zouitel.security.datasourceImpl

import city.zouitel.repository.datasource.RootDataSource
import city.zouitel.security.RootManager
import city.zouitel.security.mapper.RootMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Root as OutRoot
import city.zouitel.security.model.Root as InRoot

class RootDataSourceImpl(
    private val root: RootManager,
    private val rootMapper: RootMapper
): RootDataSource {

    override val isDeviceRooted: Flow<Result<OutRoot>>
        get() = root.isDeviceRooted().map { result ->
            result.mapCatching { rootMapper.toRepo(InRoot(it)) }
        }
}