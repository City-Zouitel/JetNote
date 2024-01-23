package city.zouitel.datastore

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import io.github.osipxd.security.crypto.createEncrypted
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val datastoreKoinModule = module {
//    includes(dispatcherKoinModule)

    single {
        PreferenceDataStoreFactory.createEncrypted(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            )
        ) {
            EncryptedFile.Builder(
                androidContext().preferencesDataStoreFile(Cons.DS_FILE),
                androidContext(),
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()
        }
    }

    singleOf(::DataStoreRepoImpl) bind DataStoreRepo::class
}

//private val dispatcherKoinModule = module {
//    single { Dispatchers.IO }
//}