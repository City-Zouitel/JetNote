package com.example.links.worker

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LinkWorker(
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val link_data = inputData.getString("link_url") ?: "it's null"

                println("""
                    *****************************************************************************
                    *****************************************************************************
                    $link_data
                    ******************************************************************************
                    ******************************************************************************
                """.trimIndent())

                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}