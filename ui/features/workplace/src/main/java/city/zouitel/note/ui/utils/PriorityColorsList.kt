package city.zouitel.note.ui.utils

import androidx.compose.ui.graphics.Color

enum class PriorityColorsList(val priority: String, val color: Color) {
    NON("NON", Color(0xffc0c0c0)),
    LOW("LOW", Color(0xff32cd32)),
    MED("MED", Color(0xffffef00)),
    HIG("HIG", Color(0xffffa500)),
    URG("URG", Color(0xffff2800));
}