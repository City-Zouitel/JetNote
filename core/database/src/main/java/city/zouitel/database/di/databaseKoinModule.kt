package city.zouitel.database.di

import androidx.room.Room
import city.zouitel.database.Database
import city.zouitel.database.Encryption
import city.zouitel.database.datasourceImpl.*
import city.zouitel.database.mapper.*
import city.zouitel.database.utils.Constants
import city.zouitel.repository.datasource.*
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseKoinModule = module {

    //DataSource.
    singleOf(::DataDataSourceImpl) bind DataDataSource::class
    singleOf(::NoteDataSourceImpl) bind NoteDataSource::class
    singleOf(::LinkDataSourceImpl) bind LinkDataSource::class
    singleOf(::NoteAndLinkDataSourceImpl) bind NoteAndLinkDataSource::class
    singleOf(::TagDataSourceImpl) bind TagDataSource::class
    singleOf(::NoteAndTagDataSourceImpl) bind NoteAndTagDataSource::class
    singleOf(::TaskDataSourceImpl) bind TaskDataSource::class
    singleOf(::NoteAndTaskDataSourceImpl) bind NoteAndTaskDataSource::class
    singleOf(::AudioDataSourceImpl) bind AudioDataSource::class
    singleOf(::NoteAndAudioDataSourceImpl) bind NoteAndAudioDataSource::class
    singleOf(::WidgetDataSourceImpl) bind WidgetDataSource::class
    singleOf(::MediaDataSourceImpl) bind MediaDataSource::class
    singleOf(::NoteAndMediaDataSourceImpl) bind NoteAndMediaDataSource::class

    //Dao's.
    single { get<Database>().getNoteDao() }
    single { get<Database>().getLabelDao() }
    single { get<Database>().getNoteAndLabelDao() }
    single { get<Database>().getEntityDao() }
    single { get<Database>().getWidgetEntityDao() }
    single { get<Database>().getTodoDao() }
    single { get<Database>().getNoteAndTodoDao() }
    single { get<Database>().getLinkDao() }
    single { get<Database>().getNoteAndLinkDao() }
    single { get<Database>().getAudioDao() }
    single { get<Database>().getNoteAndAudioDao() }
    single { get<Database>().getMediaDao() }
    single { get<Database>().getNoteAndMediaDao() }

    //Mappers
    factoryOf(::DataMapper)
    factoryOf(::LinkMapper)
    factoryOf(::TagMapper)
    factoryOf(::TaskMapper)
    factoryOf(::AudioMapper)
    factoryOf(::NoteAndLinkMapper)
    factoryOf(::NoteAndTagMapper)
    factoryOf(::NoteAndTaskMapper)
    factoryOf(::NoteAndAudioMapper)
    factoryOf(::MediaMapper)
    factoryOf(::NoteAndMediaMapper)
    factory {
        NoteMapper(get(), get(), get(), get(), get(), get())
    }
    factory {
        WidgetMapper(get(), get(), get(), get(), get(), get())
    }

    //Database.
    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            Constants.DATABASE
        )
//            .openHelperFactory(
//                SupportFactory(Encryption(androidContext()).getCrypticPass())
//            )
            .fallbackToDestructiveMigration()
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }
}