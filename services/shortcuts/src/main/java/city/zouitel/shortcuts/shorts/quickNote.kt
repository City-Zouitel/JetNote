package city.zouitel.shortcuts.shorts

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import androidx.core.graphics.drawable.IconCompat
import city.zouitel.quicknote.QuickActivity
import city.zouitel.systemDesign.CommonIcons

fun quickNote(context: Context): ShortcutInfo {
    val intent = Intent(context, QuickActivity::class.java)
    intent.action = Intent.ACTION_VIEW
    intent.putExtra("quick_note", true)
    return ShortcutInfo.Builder(context, "quick_note")
        .setShortLabel("Quick")
        .setLongLabel("Quick Note")
        .setIcon(IconCompat.createWithResource(context, CommonIcons.PLUS_ICON_18).toIcon(context))
        .setIntent(intent)
        .build()
}