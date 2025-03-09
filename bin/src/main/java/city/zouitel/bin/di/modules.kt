package city.zouitel.bin

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val binModules = module {
    factoryOf(::BinScreenModel)
    worker {
        BinWorker(androidContext(), get(), get(), get(), get(), get(), get(), get())
    }
}