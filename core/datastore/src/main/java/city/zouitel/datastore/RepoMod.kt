package city.zouitel.datastore

import city.zouitel.datastore.DataStoreRepo
import city.zouitel.datastore.DataStoreRepoImpl

//@Module
//@InstallIn(ViewModelComponent::class)
abstract class RepoMod {

//    @Binds
    abstract fun bindRepository(impl: DataStoreRepoImpl): DataStoreRepo
}