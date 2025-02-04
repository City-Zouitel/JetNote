package city.zouitel.note.ui.utils

import androidx.compose.ui.graphics.Color

enum class PriorityColorsList(val priority: Int, val color: Color) {
    NON(0, Color(0xffc0c0c0)),
    LOW(1, Color(0xff32cd32)),
    MED(2, Color(0xffffef00)),
    HIG(3, Color(0xffffa500)),
    URG(4, Color(0xffff2800));
}