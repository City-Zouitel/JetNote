package city.zouitel.datastore

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import city.zouitel.datastore.Cons
import io.github.osipxd.security.crypto.createEncrypted


//@Module
//@InstallIn(SingletonComponent::class)
object DataStoreModule {

//    @Singleton
//    @Provides
    fun provideEncryptedDataStore(
        /*@ApplicationContext*/ context: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.createEncrypted(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            )
        ) {
            EncryptedFile.Builder(
                context.preferencesDataStoreFile(Cons.DS_FILE),
                context,
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()
        }
}
