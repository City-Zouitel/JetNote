package city.zouitel.recoder.di

import city.zouitel.recoder.viewmodel.MediaRecordVM
import city.zouitel.recoder.viewmodel.RecorderVM
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recorderKoinModule = module {
    viewModelOf(::MediaRecordVM)
    viewModelOf(::RecorderVM)
}