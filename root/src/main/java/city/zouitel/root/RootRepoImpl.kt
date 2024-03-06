package city.zouitel.root

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RootRepoImpl(
    private val rootSource: RootSource
): RootRepo {

    override suspend fun isDeviceRooted(): Boolean = withContext(Dispatchers.IO) {
        rootSource.isDeviceRooted()
    }
}