package city.zouitel.shortcuts

import android.content.Context
import android.widget.Toast
import city.zouitel.logic.asShortToast
import city.zouitel.shortcuts.shorts.newNote
import city.zouitel.shortcuts.shorts.newRecord
import city.zouitel.shortcuts.shorts.quickNote

fun checkNoteActivityShortcut(context: Context) {
    val newNoteShortcut = newNote(context)
    val newRecordShortcut = newRecord(context)
    val quickNoteShortcut = quickNote(context)

    runCatching {
        context.shortcutManage.dynamicShortcuts =
            listOf(newNoteShortcut)
    }.onFailure {
        context.run {
            "something wring!".asShortToast()
        }
    }
}

fun checkQuickActivityShortcut(context: Context) {
    val quickNoteShortcut = quickNote(context)

    runCatching {
        context.shortcutManage.dynamicShortcuts =
            listOf(quickNoteShortcut)
    }.onFailure {
        context.run {
            "something wring!".asShortToast()
        }
    }
}