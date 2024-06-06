package city.zouitel.recoder.di

import city.zouitel.recoder.screenmodel.*
import city.zouitel.recoder.mapper.*
import city.zouitel.systemDesign.CommonConstants.REC_DIR
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val recorderKoinModule = module {

    factoryOf(::RecorderScreenModel)
    factory {
        MediaRecordScreenModel(get(named(REC_DIR)))
    }
    factoryOf(::AudioMapper)
    factoryOf(::NoteAndAudioMapper)
}