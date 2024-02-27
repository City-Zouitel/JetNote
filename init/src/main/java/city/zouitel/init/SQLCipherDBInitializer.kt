package city.zouitel.init

import android.content.Context
import androidx.lifecycle.ProcessLifecycleInitializer
import androidx.startup.Initializer
import net.sqlcipher.database.SQLiteDatabase

//class SQLCipherDBInitializer: Initializer<Unit?> {
//
//    override fun create(context: Context) {
//        SQLiteDatabase.loadLibs(context)
//    }
//
//    override fun dependencies(): MutableList<Class<ProcessLifecycleInitializer>> {
//        return mutableListOf(
//            ProcessLifecycleInitializer::class.java
//        )
//    }
//
//}