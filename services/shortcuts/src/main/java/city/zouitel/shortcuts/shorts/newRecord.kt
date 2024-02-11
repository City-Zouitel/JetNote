package city.zouitel.shortcuts.shorts

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import androidx.core.graphics.drawable.IconCompat
import city.zouitel.systemDesign.Icons

fun newRecord(context: Context): ShortcutInfo {
    val intent = Intent(context, context::class.java)
    intent.action = Intent.ACTION_VIEW
    intent.putExtra("new_record_shortcut", true)
    return ShortcutInfo.Builder(context, "record")
        .setShortLabel("Record")
        .setLongLabel("Record")
        .setIcon(IconCompat.createWithResource(context, Icons.MIC_ICON_18).toIcon(context))
        .setIntent(intent)
        .build()
}