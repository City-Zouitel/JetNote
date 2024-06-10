package city.zouitel.init

import androidx.compose.ui.platform.ComposeView
import city.zouitel.systemDesign.CommonConstants.IMG_DIR
import city.zouitel.systemDesign.CommonConstants.MEDIA_DIR
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

    single<String>(named(IMG_DIR)) {
        val file = File(androidContext().filesDir.path, IMG_DIR)
        if (!file.exists()) file.mkdirs()
        file.path
    }

//    single<String>(named(LINK_DIR)) {
//        val file = File(androidContext().filesDir.path, LINK_DIR)
//        if (!file.exists()) file.mkdirs()
//        file.path
//    }

    single<String>(named(MEDIA_DIR)) {
        val file = File(androidContext().filesDir.path, MEDIA_DIR)
        if (!file.exists()) file.mkdirs()
        file.path
    }
}