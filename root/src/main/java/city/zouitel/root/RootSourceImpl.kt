package city.zouitel.root

class RootSourceImpl(
    private val root: Root
): RootSource {

    override suspend fun isDeviceRooted(): Boolean = root.isDeviceRooted()
}