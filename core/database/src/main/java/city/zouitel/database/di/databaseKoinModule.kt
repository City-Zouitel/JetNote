package city.zouitel.database.di

import androidx.room.Room
import city.zouitel.database.Database
import city.zouitel.database.Encryption
import city.zouitel.database.datasourceImpl.DataDataSourceImpl
import city.zouitel.database.datasourceImpl.LinkDataSourceImpl
import city.zouitel.database.datasourceImpl.NoteAndLinkDataSourceImpl
import city.zouitel.database.datasourceImpl.NoteAndTagDataSourceImpl
import city.zouitel.database.datasourceImpl.NoteAndTaskDataSourceImpl
import city.zouitel.database.datasourceImpl.NoteDataSourceImpl
import city.zouitel.database.datasourceImpl.TagDataSourceImpl
import city.zouitel.database.datasourceImpl.TaskDataSourceImpl
import city.zouitel.database.datasourceImpl.WidgetDataSourceImpl
import city.zouitel.database.mapper.DataMapper
import city.zouitel.database.mapper.LinkMapper
import city.zouitel.database.mapper.NoteAndLinkMapper
import city.zouitel.database.mapper.NoteAndTagMapper
import city.zouitel.database.mapper.NoteAndTaskMapper
import city.zouitel.database.mapper.NoteMapper
import city.zouitel.database.mapper.TagMapper
import city.zouitel.database.mapper.TaskMapper
import city.zouitel.database.mapper.WidgetMapper
import city.zouitel.database.utils.Constants
import city.zouitel.repository.datasource.DataDataSource
import city.zouitel.repository.datasource.LinkDataSource
import city.zouitel.repository.datasource.NoteAndLinkDataSource
import city.zouitel.repository.datasource.NoteAndTagDataSource
import city.zouitel.repository.datasource.NoteAndTaskDataSource
import city.zouitel.repository.datasource.NoteDataSource
import city.zouitel.repository.datasource.TagDataSource
import city.zouitel.repository.datasource.TaskDataSource
import city.zouitel.repository.datasource.WidgetDataSource
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseKoinModule = module {

    //DataSource.
    singleOf(::LinkDataSourceImpl) bind LinkDataSource::class
    singleOf(::DataDataSourceImpl) bind DataDataSource::class
    singleOf(::NoteAndLinkDataSourceImpl) bind NoteAndLinkDataSource::class
    singleOf(::NoteAndTagDataSourceImpl) bind NoteAndTagDataSource::class
    singleOf(::NoteAndTaskDataSourceImpl) bind NoteAndTaskDataSource::class
    singleOf(::NoteDataSourceImpl) bind NoteDataSource::class
    singleOf(::TagDataSourceImpl) bind TagDataSource::class
    singleOf(::TaskDataSourceImpl) bind TaskDataSource::class
    singleOf(::WidgetDataSourceImpl) bind WidgetDataSource::class

    //Dao's.
    single { get<Database>().getNoteDao() }
    single { get<Database>().getLabelDao() }
    single { get<Database>().getNoteAndLabelDao() }
    single { get<Database>().getEntityDao() }
    single { get<Database>().getWidgetEntityDao() }
    single { get<Database>().getTodoDao() }
    single { get<Database>().getNoteAndTodoDao() }
    single { get<Database>().getLinkDao() }
    single { get<Database>().getNoteAndLink() }

    //Mappers
    factoryOf(::DataMapper)
    factoryOf(::LinkMapper)
    factoryOf(::TagMapper)
    factoryOf(::TaskMapper)
    factoryOf(::NoteAndLinkMapper)
    factoryOf(::NoteAndTagMapper)
    factoryOf(::NoteAndTaskMapper)
    factory {
        NoteMapper(get(), get(), get(), get())
    }
    factory {
        WidgetMapper(get(), get(), get(), get())
    }

    //Database.
    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            Constants.DATABASE_NAME
        )
//            .openHelperFactory(
//            SupportFactory(Encryption(androidContext()).getCrypticPass())
//        )
            .fallbackToDestructiveMigration()
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }
}