package com.example.graph

import android.content.Context
import com.example.common_ui.Cons.AUDIOS
import com.example.common_ui.Cons.IMAGES
import com.example.common_ui.Cons.JPEG
import com.example.common_ui.Cons.MP3
import com.example.note.DataViewModel
import com.example.note.model.Data
import java.io.File
import java.util.*

//
fun copyNote(
    ctx: Context,
    dataViewModel: DataViewModel,
    note: Data,
    uid: UUID,
    cc : () -> Unit
) {
    dataViewModel.addData(
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