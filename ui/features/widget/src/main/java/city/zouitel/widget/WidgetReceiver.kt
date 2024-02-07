package city.zouitel.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import city.zouitel.widget.ui.AppWidget

class WidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = AppWidget()

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            ACTION_APPS_UPDATED -> {
                val awm = AppWidgetManager.getInstance(context)
                val ids = ComponentName(context.packageName, checkNotNull(javaClass.canonicalName))
                onUpdate(
                    context = context,
                    appWidgetManager = awm,
                    appWidgetIds = awm.getAppWidgetIds(ids)
                )
            }
            else -> super.onReceive(context, intent)
        }
    }

    companion object {
        const val ACTION_APPS_UPDATED = "city.zouitel.jetnote" + ".action.ACTION_APPS_UPDATED"

        @JvmStatic
        fun updateBroadcast(ctx: Context) {
            ctx.sendBroadcast(
                Intent(ctx, WidgetReceiver::class.java).also {
                    it.action = ACTION_APPS_UPDATED
                }
            )
        }
    }

}