package city.zouitel.navigation

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.widget.Toast
import androidx.core.graphics.drawable.IconCompat

fun appShortcut(
    ctx: Context,
): ShortcutInfo {
    val intent = Intent(ctx, ctx::class.java)
    intent.action = Intent.ACTION_VIEW
    intent.putExtra("new_shortcut", true)
    return ShortcutInfo.Builder(ctx, "shortcut")
        .setShortLabel("Compose")
        .setLongLabel("Compose")
        .setIcon(IconCompat.createWithResource(ctx, R.drawable.baseline_add_24).toIcon(ctx))
        .setIntent(intent)
        .build()
}

fun checkShortcut(
    ctx: Context
) {
    val newNoteShortcut = appShortcut(ctx)
    runCatching {
        ctx.shortcutManage.dynamicShortcuts = listOf(newNoteShortcut)
    }.onFailure {
        Toast.makeText(ctx, "something wring!", Toast.LENGTH_SHORT).show()
    }
}

val Context.shortcutManage: ShortcutManager
    get() = getSystemService(ShortcutManager::class.java) as ShortcutManager