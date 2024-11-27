package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Root as OutRoot
import city.zouitel.domain.repository.RootRepository
import city.zouitel.repository.datasource.RootDataSource
import city.zouitel.repository.mapper.RootMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RootRepositoryImpl(
    private val dataSource: RootDataSource,
    private val mapper: RootMapper
): RootRepository {
    override val isDeviceRooted: Flow<Result<OutRoot>>
        get() = dataSource.isDeviceRooted.map { result ->
            result.mapCatching { mapper.toDomain(it) }
        }
}