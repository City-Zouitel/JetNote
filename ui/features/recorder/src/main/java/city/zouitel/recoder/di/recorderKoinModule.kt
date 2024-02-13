package city.zouitel.recoder.di

import city.zouitel.recoder.viewmodel.MediaRecordVM
import city.zouitel.recoder.viewmodel.RecorderVM
import city.zouitel.systemDesign.Cons.AUDIOS
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

val recorderKoinModule = module {
    viewModelOf(::MediaRecordVM)
    viewModelOf(::RecorderVM)
}