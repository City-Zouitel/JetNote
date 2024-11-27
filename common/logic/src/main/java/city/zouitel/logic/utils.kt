package city.zouitel.logic

import android.content.Context

infix fun String.getRecPath(context: Context): String {
    return context.filesDir.path + "/records-directory/" + this + "." + "mp3"
}