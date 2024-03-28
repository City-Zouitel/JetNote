package city.zouitel.recoder.di

import city.zouitel.recoder.viewmodel.MediaRecordScreenModel
import city.zouitel.recoder.viewmodel.RecorderScreenModel
import city.zouitel.systemDesign.Cons.REC_DIR
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val recorderKoinModule = module {
    factory {
        MediaRecordScreenModel(get(named(REC_DIR)))
    }
    factoryOf(::RecorderScreenModel)
}