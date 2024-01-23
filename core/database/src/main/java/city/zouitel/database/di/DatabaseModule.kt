package city.zouitel.database.di

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import city.zouitel.database.utils.Constants.DATABASE_NAME
import city.zouitel.database.Database
import city.zouitel.database.Encryption
import net.sqlcipher.database.SupportFactory

//@Module
//@InstallIn(SingletonComponent::class)
object DatabaseModule {

//    @Provides
//    @Singleton
    fun provideEncryption(application: Application) = Encryption(application)

//    @Provides
//    @Singleton
    fun provideSupportEncryption(encryption: Encryption) =
        SupportFactory(encryption.getCrypticPass())

//    @Singleton
//    @Provides
    fun provideDatabase(
        /*@ApplicationContext*/ context: Context,
        supportFactory: SupportFactory
    ) = Room.databaseBuilder(
        context.applicationContext,
        Database::class.java,
        DATABASE_NAME
    ).openHelperFactory(supportFactory)
        .fallbackToDestructiveMigration()
        .fallbackToDestructiveMigrationOnDowngrade()
        .build()
}