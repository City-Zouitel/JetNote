package city.zouitel.root

class RootUseCase(
    private val rootRepo: RootRepo
) {

    suspend operator fun invoke() = rootRepo.isDeviceRooted()
}