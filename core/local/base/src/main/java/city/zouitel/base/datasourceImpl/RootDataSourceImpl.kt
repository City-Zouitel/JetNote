package city.zouitel.base.datasourceImpl

import city.zouitel.base.RootManager
import city.zouitel.base.mapper.RootMapper
import city.zouitel.repository.datasource.RootDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.base.model.Root as InRoot
import city.zouitel.repository.model.Root as OutRoot

class RootDataSourceImpl(
    private val root: RootManager,
    private val rootMapper: RootMapper
): RootDataSource {

    override val isDeviceRooted: Flow<Result<OutRoot>>
        get() = root.isDeviceRooted().map { result ->
            result.mapCatching { rootMapper.toRepo(InRoot(it)) }
        }
}