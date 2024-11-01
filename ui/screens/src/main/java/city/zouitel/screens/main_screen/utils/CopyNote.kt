package city.zouitel.screens.main_screen.utils

import android.content.Context
import city.zouitel.logic.events.UiEvent
import city.zouitel.note.model.Data
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.systemDesign.CommonConstants
import java.io.File
import java.util.UUID

fun copyNote(
    ctx: Context,
    dataModel: DataScreenModel,
    note: Data,
    uid: UUID,
    onAction : () -> Unit
) {
//    dataModel.addData(note.copy(uid = uid.toString()))
    dataModel.sendUiEvent(UiEvent.Insert(note.copy(uid = uid.toString())))
    //
//    "${ctx.filesDir.path}/${CommonConstants.IMG_DIR}/".apply {
//        File("$this${note.uid}.${CommonConstants.JPEG}").let {
//            if (it.exists()) it.copyTo(File("${this}${uid}.${CommonConstants.JPEG}"))
//        }
//    }
    //
    "${ctx.filesDir.path}/${CommonConstants.REC_DIR}/".apply {
        File("$this${note.uid}.${CommonConstants.MP3}").let {
            if (it.exists()) it.copyTo(File("$this${uid}.${CommonConstants.MP3}"))
        }
    }
    //
    onAction.invoke()
}
