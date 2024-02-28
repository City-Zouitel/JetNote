package city.zouitel.init

import android.content.Context
import androidx.lifecycle.ProcessLifecycleInitializer
import city.zouitel.systemDesign.Cons
import com.rousetime.android_startup.AndroidStartup
import java.io.File

class DirectoriesInitializer : AndroidStartup<Boolean>() {
    override fun callCreateOnMainThread() = true

    override fun create(context: Context): Boolean {
        for (d in directories) {
            if (!File(context.filesDir, d).exists()) {
                File(context.filesDir, d).mkdirs()
            }
        }
        return directories.all { File(context.filesDir, it).exists() }
    }

    override fun waitOnMainThread(): Boolean = true

    companion object {

        private val directories = listOf(Cons.IMG_DIR, Cons.REC_DIR, Cons.LINK_DIR)
    }

    override fun dependenciesByName(): List<String> {
        return listOf(ProcessLifecycleInitializer::class.java.name)
    }
}