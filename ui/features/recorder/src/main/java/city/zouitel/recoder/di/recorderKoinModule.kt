package city.zouitel.recoder.di

import city.zouitel.recoder.viewmodel.MediaRecordVM
import city.zouitel.recoder.viewmodel.RecorderVM
import city.zouitel.systemDesign.Cons.REC_DIR
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val recorderKoinModule = module {
    viewModel {
        MediaRecordVM(get(named(REC_DIR)))
    }
    viewModelOf(::RecorderVM)
}