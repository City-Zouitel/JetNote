package city.zouitel.navigation

import android.content.Context
import city.zouitel.note.DataViewModel
import city.zouitel.note.model.Data
import city.zouitel.systemDesign.Cons.AUDIOS
import city.zouitel.systemDesign.Cons.IMAGES
import city.zouitel.systemDesign.Cons.JPEG
import city.zouitel.systemDesign.Cons.MP3
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