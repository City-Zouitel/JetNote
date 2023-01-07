package com.example.jetnote.ui.note_card

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.example.jetnote.db.entities.note.Note
import com.example.jetnote.fp.getColorOfPriority

val DrawScope.clipNotePath: (Note) -> Unit get() = {
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
            cornerRadius = CornerRadius(10.dp.toPx())
        )
        drawRoundRect(
            color = Color(
                ColorUtils.blendARGB(getColorOfPriority(it.priority).toArgb(), 0x000000, .05f)
            ),
            topLeft = Offset(size.width - 25.dp.toPx(), size.height - 25.dp.toPx()),
            size = Size(
                25.dp.toPx() + 100f, 25.dp.toPx() + 100f
            ),
            cornerRadius = CornerRadius(10.dp.toPx())
        )
    }
}

val DrawScope.normalNotePath: (Note) -> Unit get() = {
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
            cornerRadius = CornerRadius(10.dp.toPx())
        )
    }
}


val AnnotatedString.Builder.hashtagText: (Note, String) -> Unit get() = { note, txt ->
    for (link in txt.split(' ')) {
        if (link.matches("(.*|\\n)#\\w+|https?://.+|\\d{3}+(.*|\\n)".toRegex())) {
            withStyle(SpanStyle(color = Color.Cyan, fontSize = 19.sp)) {
                append("$link ")
            }
        } else {
            withStyle(SpanStyle(color = Color(note.textColor), fontSize = 19.sp)) {
                append("$link ")
            }
        }
    }
}

