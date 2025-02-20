package city.zouitel.init

import androidx.compose.ui.platform.ComposeView
import city.zouitel.systemDesign.CommonConstants.REC_DIR
import net.sqlcipher.database.SQLiteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

val initializerKoinModule = module {
    single {
        ComposeView(androidContext())
    }

    single {
        SQLiteDatabase.loadLibs(androidContext())
    }

    single<String>(named(REC_DIR)) {
        val file = File(androidContext().filesDir.path, REC_DIR)
        if (!file.exists()) file.mkdirs()
        file.path
    }
}