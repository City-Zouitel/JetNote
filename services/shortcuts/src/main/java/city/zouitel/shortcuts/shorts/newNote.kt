package city.zouitel.shortcuts.shorts

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import androidx.core.graphics.drawable.IconCompat
import city.zouitel.systemDesign.CommonIcons

fun newNote(context: Context): ShortcutInfo {
    val intent = Intent(context, context::class.java)
    intent.action = Intent.ACTION_VIEW
    intent.putExtra("new_note_shortcut", true)
    return ShortcutInfo.Builder(context, "note")
        .setShortLabel("Compose")
        .setLongLabel("Compose")
        .setIcon(IconCompat.createWithResource(context, CommonIcons.PLUS_ICON_18).toIcon(context))
        .setIntent(intent)
        .build()
}