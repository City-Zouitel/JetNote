package city.zouitel.screens

import android.content.Context
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import city.zouitel.logic.getColorOfPriority
import city.zouitel.note.model.Data
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.SoundEffect
import java.io.File
import java.util.UUID

internal val sound = SoundEffect()



val DrawScope.prioritizedNotePath: (Data) -> Unit get() = {
    clipPath(
        Path().apply {
            lineTo(size.width, 0f)
            lineTo(size.width,size.height - 25.dp.toPx())
            lineTo(size.width - 25.dp.toPx(), size.height)
            lineTo(0f,size.height)
            close()
        }
    ) {

        drawRoundRect(
            color = Color(it.color),
            size = size,
            cornerRadius = CornerRadius(15.dp.toPx())
        )
        drawRoundRect(
            color = Color(
                ColorUtils.blendARGB(getColorOfPriority(it.priority), 0x000000, .05f)
            ),
            topLeft = Offset(size.width - 25.dp.toPx(), size.height - 25.dp.toPx()),
            size = Size(
                25.dp.toPx() + 100f, 25.dp.toPx() + 100f
            ),
            cornerRadius = CornerRadius(15.dp.toPx())
        )
    }
}

val DrawScope.normalNotePath: (Data) -> Unit get() = {
    clipPath(
        Path().apply {
            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
    ) {
        drawRoundRect(
            color = Color(it.color),
            size = size,
            cornerRadius = CornerRadius(15.dp.toPx())
        )
    }
}

fun copyNote(
    ctx: Context,
    dataScreenModel: DataScreenModel,
    note: Data,
    uid: UUID,
    onAction : () -> Unit
) {
    dataScreenModel.addData(
        note.copy(uid = uid.toString())
    )
    //
    "${ctx.filesDir.path}/${CommonConstants.IMG_DIR}/".apply {
        File("$this${note.uid}.${CommonConstants.JPEG}").let {
            if (it.exists()) it.copyTo(File("${this}${uid}.${CommonConstants.JPEG}"))
        }
    }
    //
    "${ctx.filesDir.path}/${CommonConstants.REC_DIR}/".apply {
        File("$this${note.uid}.${CommonConstants.MP3}").let {
            if (it.exists()) it.copyTo(File("$this${uid}.${CommonConstants.MP3}"))
        }
    }
    //
    onAction.invoke()
}

