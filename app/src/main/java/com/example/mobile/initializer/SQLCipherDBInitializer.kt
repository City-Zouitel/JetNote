package com.example.mobile.initializer

import android.content.Context
import com.rousetime.android_startup.AndroidStartup
import net.sqlcipher.database.SQLiteDatabase

internal class SQLCipherDBInitializer: AndroidStartup<Unit?>() {
    override fun callCreateOnMainThread(): Boolean = true

    override fun create(context: Context) {
        SQLiteDatabase.loadLibs(context)
    }

    override fun waitOnMainThread(): Boolean = false

//    override fun dependencies(): MutableList<Class<ProcessLifecycleInitializer>> {
//        return mutableListOf(
//            ProcessLifecycleInitializer::class.java
//        )
//    }
}