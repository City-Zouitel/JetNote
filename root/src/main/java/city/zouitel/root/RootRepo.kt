package city.zouitel.root

interface RootRepo {

    suspend fun isDeviceRooted(): Boolean

}
