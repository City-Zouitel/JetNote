package city.zouitel.database.di

import androidx.room.Room
import city.zouitel.database.Database
import city.zouitel.database.Encryption
import city.zouitel.database.datasourceImpl.AudioDataSourceImpl
import city.zouitel.database.datasourceImpl.DataDataSourceImpl
import city.zouitel.database.datasourceImpl.LinkDataSourceImpl
import city.zouitel.database.datasourceImpl.MediaDataSourceImpl
import city.zouitel.database.datasourceImpl.NoteAndTagDataSourceImpl
import city.zouitel.database.datasourceImpl.NoteDataSourceImpl
import city.zouitel.database.datasourceImpl.ReminderDataSourceImpl
import city.zouitel.database.datasourceImpl.TagDataSourceImpl
import city.zouitel.database.datasourceImpl.TaskDataSourceImpl
import city.zouitel.database.datasourceImpl.WidgetDataSourceImpl
import city.zouitel.database.mapper.AudioMapper
import city.zouitel.database.mapper.DataMapper
import city.zouitel.database.mapper.LinkMapper
import city.zouitel.database.mapper.MediaMapper
import city.zouitel.database.mapper.NoteAndTagMapper
import city.zouitel.database.mapper.NoteMapper
import city.zouitel.database.mapper.ReminderMapper
import city.zouitel.database.mapper.TagMapper
import city.zouitel.database.mapper.TaskMapper
import city.zouitel.database.mapper.WidgetMapper
import city.zouitel.database.utils.Constants
import city.zouitel.repository.datasource.AudioDataSource
import city.zouitel.repository.datasource.DataDataSource
import city.zouitel.repository.datasource.LinkDataSource
import city.zouitel.repository.datasource.MediaDataSource
import city.zouitel.repository.datasource.NoteAndTagDataSource
import city.zouitel.repository.datasource.NoteDataSource
import city.zouitel.repository.datasource.ReminderDataSource
import city.zouitel.repository.datasource.TagDataSource
import city.zouitel.repository.datasource.TaskDataSource
import city.zouitel.repository.datasource.WidgetDataSource
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseDIModule = module {

    //DataSource.
    singleOf(::DataDataSourceImpl) bind DataDataSource::class
    singleOf(::NoteDataSourceImpl) bind NoteDataSource::class
    singleOf(::LinkDataSourceImpl) bind LinkDataSource::class
    singleOf(::TagDataSourceImpl) bind TagDataSource::class
    singleOf(::NoteAndTagDataSourceImpl) bind NoteAndTagDataSource::class
    singleOf(::TaskDataSourceImpl) bind TaskDataSource::class
    singleOf(::AudioDataSourceImpl) bind AudioDataSource::class
    singleOf(::WidgetDataSourceImpl) bind WidgetDataSource::class
    singleOf(::MediaDataSourceImpl) bind MediaDataSource::class
    singleOf(::ReminderDataSourceImpl) bind ReminderDataSource::class

    //Dao's.
    single { get<Database>().getNoteDao() }
    single { get<Database>().getLabelDao() }
    single { get<Database>().getNoteAndLabelDao() }
    single { get<Database>().getEntityDao() }
    single { get<Database>().getWidgetEntityDao() }
    single { get<Database>().getTaskDao() }
    single { get<Database>().getLinkDao() }
    single { get<Database>().getAudioDao() }
    single { get<Database>().getMediaDao() }
    single { get<Database>().getReminderDao() }

    //Mappers
    factoryOf(::DataMapper)
    factoryOf(::LinkMapper)
    factoryOf(::TagMapper)
    factoryOf(::TaskMapper)
    factoryOf(::AudioMapper)
    factoryOf(::NoteAndTagMapper)
    factoryOf(::MediaMapper)
    factoryOf(::ReminderMapper)
    factory {
        NoteMapper(get(), get())
    }
    factory {
        WidgetMapper(get(), get())
    }

    //Database.
    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            Constants.DATABASE
        )
            .openHelperFactory(SupportFactory(Encryption(androidContext()).getCrypticPass()))
            .fallbackToDestructiveMigration()
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }
}