package city.zouitel.init

import android.content.Context
import androidx.lifecycle.ProcessLifecycleInitializer
import androidx.startup.Initializer
import city.zouitel.systemDesign.Cons
import java.io.File

//class FilesInitializer : Initializer<Boolean> {
//
//    override fun create(context: Context): Boolean {
//        for (d in directories) {
//            if (!File(context.filesDir, d).exists()) {
//                File(context.filesDir, d).mkdirs()
//            }
//        }
//        return directories.all { File(context.filesDir, it).exists() }
//    }
//
//    override fun dependencies(): MutableList<Class<ProcessLifecycleInitializer>> {
//        return mutableListOf(
//            ProcessLifecycleInitializer::class.java
//        )
//    }
//    companion object {
//
//        private val directories = listOf(Cons.IMG_DIR, Cons.REC_DIR, Cons.LINK_DIR)
//    }
//}