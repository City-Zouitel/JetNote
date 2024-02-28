package city.zouitel.init

import android.content.Context
import androidx.lifecycle.ProcessLifecycleInitializer
import androidx.startup.Initializer
import com.rousetime.android_startup.AndroidStartup
import net.sqlcipher.database.SQLiteDatabase

class SQLCipherDBInitializer : AndroidStartup<Unit>() {
    override fun callCreateOnMainThread() = true

    override fun create(context: Context) {
        return SQLiteDatabase.loadLibs(context)
    }

    override fun waitOnMainThread() = true

    override fun dependenciesByName(): List<String> {
        return listOf(ProcessLifecycleInitializer::class.java.name)
    }
}