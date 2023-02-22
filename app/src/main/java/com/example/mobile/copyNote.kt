package com.example.mobile.home_screen

import android.content.Context
import com.example.common_ui.Cons.AUDIOS
import com.example.common_ui.Cons.IMAGES
import com.example.common_ui.Cons.JPEG
import com.example.common_ui.Cons.MP3
import com.example.local.model.Note
import com.example.note.NoteVM
import java.io.File
import java.util.*

//
fun copyNote(
    ctx: Context,
    noteVM: NoteVM,
    note: Note,
    uid: UUID,
    cc : () -> Unit
) {
    noteVM.addNote(
        note.copy(uid = uid.toString())
    )
    //
    "${ctx.filesDir.path}/$IMAGES/".apply {
        File("$this${note.uid}.$JPEG").let {
            if (it.exists()) it.copyTo(File("${this}${uid}.$JPEG"))
        }
    }
    //
    "${ctx.filesDir.path}/$AUDIOS/".apply {
        File("$this${note.uid}.$MP3").let {
            if (it.exists()) it.copyTo(File("$this${uid}.$MP3"))
        }
    }
    //
    cc.invoke()
}