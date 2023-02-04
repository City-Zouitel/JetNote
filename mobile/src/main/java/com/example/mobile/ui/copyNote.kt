package com.example.mobile.ui

import android.content.Context
import com.example.local.model.Note
import com.example.mobile.cons.AUDIO_FILE
import com.example.mobile.cons.IMAGE_FILE
import com.example.mobile.cons.JPEG
import com.example.mobile.cons.MP3
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
    "${ctx.filesDir.path}/$IMAGE_FILE/".apply {
        File("$this${note.uid}.$JPEG").let {
            if (it.exists()) it.copyTo(File("${this}${uid}.$JPEG"))
        }
    }
    //
    "${ctx.filesDir.path}/$AUDIO_FILE/".apply {
        File("$this${note.uid}.$MP3").let {
            if (it.exists()) it.copyTo(File("$this${uid}.$MP3"))
        }
    }
    //
    cc.invoke()
}