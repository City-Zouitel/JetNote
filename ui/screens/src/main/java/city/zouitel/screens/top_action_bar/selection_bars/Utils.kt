package city.zouitel.screens.top_action_bar.selection_bars

import android.content.Context
import city.zouitel.note.DataScreenModel
import city.zouitel.note.model.Data
import city.zouitel.systemDesign.Cons.REC_DIR
import city.zouitel.systemDesign.Cons.IMG_DIR
import city.zouitel.systemDesign.Cons.JPEG
import city.zouitel.systemDesign.Cons.MP3
import java.io.File
import java.util.*

fun copyNote(
    ctx: Context,
    dataScreenModel: DataScreenModel,
    note: Data,
    uid: UUID,
    cc : () -> Unit
) {
    dataScreenModel.addData(
        note.copy(uid = uid.toString())
    )
    //
    "${ctx.filesDir.path}/$IMG_DIR/".apply {
        File("$this${note.uid}.$JPEG").let {
            if (it.exists()) it.copyTo(File("${this}${uid}.$JPEG"))
        }
    }
    //
    "${ctx.filesDir.path}/$REC_DIR/".apply {
        File("$this${note.uid}.$MP3").let {
            if (it.exists()) it.copyTo(File("$this${uid}.$MP3"))
        }
    }
    //
    cc.invoke()
}