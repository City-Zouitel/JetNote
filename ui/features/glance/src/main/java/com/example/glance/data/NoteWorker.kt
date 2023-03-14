package com.example.glance.data

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.*
import com.example.glance.model.GlanceNote
import com.example.glance.presentation.NoteGlanceWidget
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NoteWorker(
private val ctx:Context,
workerParameters: WorkerParameters
):CoroutineWorker(ctx, workerParameters),KoinComponent {

    private val vm: NoteGlanceUpdater by inject()

    override suspend fun doWork(): Result {
        val manager = GlanceAppWidgetManager(ctx)

        val glanceIds = manager.getGlanceIds(NoteGlanceWidget::class.java)
        val notes = vm.loadNote().first()
        update(glanceIds, notes)
        return Result.success()
    }

    private suspend fun update(glanceIds: List<GlanceId>, notes: List<GlanceNote>) {
        glanceIds.forEach {
            updateAppWidgetState(
                context = ctx,
                definition = NoteStateDefinition,
                glanceId = it,
                updateState ={ notes.toImmutableList() }
            )
            NoteGlanceWidget().updateAll(ctx)
        }
    }
    companion object {
        private val workName = NoteWorker::class.java.simpleName
        fun enq(ctx: Context) {
            val manager =WorkManager.getInstance(ctx)
            val build = OneTimeWorkRequestBuilder<NoteWorker>()
            manager.enqueueUniqueWork(
                workName,
                ExistingWorkPolicy.KEEP,
                build.build()
            )
        }

        fun cancel(ctx: Context) {
            WorkManager.getInstance(ctx).cancelUniqueWork(workName)
        }
    }
}