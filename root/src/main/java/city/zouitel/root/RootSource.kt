package city.zouitel.root

interface RootSource {

    suspend fun isDeviceRooted(): Boolean
}