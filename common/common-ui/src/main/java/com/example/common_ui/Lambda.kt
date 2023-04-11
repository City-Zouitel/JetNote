package com.example.common_ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.baha.url.preview.BahaUrlPreview
import com.baha.url.preview.IUrlPreviewCallback
import com.baha.url.preview.UrlInfoItem
import com.example.common_ui.Cons.IMPORTANT
import com.example.common_ui.Cons.LOW
import com.example.common_ui.Cons.NON
import com.example.common_ui.Cons.NORMAL
import com.example.common_ui.Cons.URGENT
import kotlinx.coroutines.*
import java.net.URL
import java.util.*

//
val sharApp: (Context, String) -> Unit = { ctx, txt ->
    ctx.startActivity(
        Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, txt)
                type = "text/plain"
            },
            null
        )
    )
}

//
val sharNote: (Context, String, String, then: () -> Unit) -> Unit = { ctx, title, description ,then ->
    ctx.startActivity(
        Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "$title\n\n$description")
                type = "text/plain"
            },
            null
        )
    )
    then.invoke()
}

val mailTo: (Context, String) -> Unit = { ctx, to ->
    ctx.startActivity(
        Intent.createChooser(
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse(to)
            },
            null
        )
    )
}

//
val callNumber: (Context,String) -> Unit = { ctx, number ->
    ctx.startActivity(
        Intent.createChooser(
            Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$number")
            },
            null
        )
    )
}

//
val findUrlLink: (String?) -> String? = {
    it?.split(' ')?.find { str -> str.matches("https?://.+".toRegex()) }
}

//
@JvmName("filterBadWordsString")
fun MutableState<String?>.filterBadWords(): MutableState<String?> {
    value?.split(' ')?.onEach {
//        if (
//            it.lowercase() in listOfBadEnglishWords
//                .plus(badItian)
//                .plus(badSpanish)
//                .plus(badGerman)
//                .plus(badFrench)
//        )
            value = value!!.replace(
                it,
                "${it.first()}${it.map { '*' }.joinToString("").drop(2)}${it.last()}",
                true
            )
    }
    return this
}

@JvmName("filterNonNullableBadWordsString")
fun MutableState<String>.filterBadWords(): MutableState<String> {
    value.split(' ').onEach {
//        if (
//            it.lowercase() in listOfBadEnglishWords
//                .plus(badItian)
//                .plus(badSpanish)
//                .plus(badGerman)
//                .plus(badFrench)
//        )
            value = value.replace(
                it,
                "${it.first()}${it.map { '*' }.joinToString("").drop(2)}${it.last()}",
                true
            )
    }
    return this
}

fun MutableState<String?>.filterBadEmoji(): MutableState<String?> {
//    listOfBadEmojis.entries.forEach {
//        if (this.value?.contains(it.key) == true)
//            this.value = this.value!!.replace(it.key,it.value)
//    }
    return this
}

//
fun MutableState<String?>.filterBadWebsites():MutableState<String?> {
    findUrlLink(this.value)?.let {
//        if (URL(this@filterBadWebsites.value).host in listOfBadWebsites ) {
//            this@filterBadWebsites.value = "invalid subject!"
//            this
//        } else {
//            this
//        }
    }
    return this
}

//
val getPriorityOfColor: (Color) -> String = {
    when (it) {
        Color.Red -> URGENT
        Color.Yellow -> IMPORTANT
        Color.Green -> NORMAL
        Color.Cyan -> LOW
        else -> {
            NON
        }
    }
}

    //
val getColorOfPriority: (String) -> Color = {
    when (it) {
        URGENT -> Color.Red
        IMPORTANT -> Color.Yellow
        NORMAL -> Color.Green
        LOW -> Color.Cyan
        else -> {
            Color.Transparent
        }
    }
}

//
val codeUrl: (String?) -> String?
    get() = {
        it?.replace(
            '\u002f',// -> /
            '\u2204' // -> ∄
        )
            ?.replace(
                '\u003f',// -> ?
                '\u2203' // -> 	∃
            )
    }

val decodeUrl: (String?) -> String?
    get() = {
        it?.replace(
            '\u2204', // -> ∄
            '\u002f' // -> /
        )
            ?.replace(
                '\u2203', // -> 	∃
                '\u003f' // -> ?
            )
    }



