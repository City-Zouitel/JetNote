package com.example.mobile.initializer

import android.content.Context
import androidx.lifecycle.ProcessLifecycleInitializer
import androidx.startup.Initializer
import net.sqlcipher.database.SQLiteDatabase

internal class SQLCipherDBInitializer: Initializer<Unit?> {
    override fun create(context: Context) {
        SQLiteDatabase.loadLibs(context)
    }

    override fun dependencies(): MutableList<Class<ProcessLifecycleInitializer>> {
        return mutableListOf(
            ProcessLifecycleInitializer::class.java
        )
    }
}