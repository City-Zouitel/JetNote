package city.zouitel.shortcuts

import android.content.Context
import android.widget.Toast
import city.zouitel.shortcuts.shorts.newNote
import city.zouitel.shortcuts.shorts.newRecord
import city.zouitel.shortcuts.shorts.quickNote

fun checkNoteActivityShortcut(context: Context) {
    val newNoteShortcut = newNote(context)
    val newRecordShortcut = newRecord(context)
    val quickNoteShortcut = quickNote(context)

    runCatching {
        context.shortcutManage.dynamicShortcuts =
            listOf(newNoteShortcut, /*newRecordShortcut,*/ /*quickNoteShortcut*/)
    }.onFailure {
        Toast.makeText(context, "something wring!", Toast.LENGTH_SHORT).show()
    }
}

fun checkQuickActivityShortcut(context: Context) {
    val quickNoteShortcut = quickNote(context)

    runCatching {
        context.shortcutManage.dynamicShortcuts =
            listOf(quickNoteShortcut)
    }.onFailure {
        Toast.makeText(context, "something wring!", Toast.LENGTH_SHORT).show()
    }
}