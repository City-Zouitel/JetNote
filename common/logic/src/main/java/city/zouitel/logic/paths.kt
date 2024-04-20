package city.zouitel.logic

import android.content.Context
import kotlin.properties.Delegates

infix fun String.getRecPath(context: Context): String {
    return context.filesDir.path + "/records-directory/" + this + "." + "mp3"
}

infix fun String.getImgPath(context: Context): String {
    return context.filesDir.path + "/images-directory/" + this + "." + "jpeg"
}