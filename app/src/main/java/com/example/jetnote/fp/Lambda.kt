package com.example.jetnote.fp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.baha.url.preview.BahaUrlPreview
import com.baha.url.preview.IUrlPreviewCallback
import com.baha.url.preview.UrlInfoItem
import com.example.jetnote.combat.badLanguageWords.listOfBadEnglishWords
import com.example.jetnote.combat.listOfBadWebsites
import com.example.jetnote.cons.*
import com.example.jetnote.db.entities.note.Note
import com.example.jetnote.vm.NoteVM
import kotlinx.coroutines.*
import java.io.File
import java.net.URL
import java.util.*

//
internal val getMaterialColor: @Composable (String) -> Color = {
    mapOf(
        SURFACE to MaterialTheme.colorScheme.surface,
        INVERSE_SURFACE to MaterialTheme.colorScheme.inverseSurface,
        ON_SURFACE to MaterialTheme.colorScheme.onSurface,
        INVERSE_ON_SURFACE to MaterialTheme.colorScheme.inverseOnSurface,
        SURFACE_VARIANT to MaterialTheme.colorScheme.surfaceVariant,
        ON_SURFACE_VARIANT to MaterialTheme.colorScheme.onSurfaceVariant,
        SURFACE_TINT to MaterialTheme.colorScheme.surfaceTint,
        ERROR to MaterialTheme.colorScheme.error,
        ON_ERROR to MaterialTheme.colorScheme.onError,
        ERROR_CONTAINER to MaterialTheme.colorScheme.errorContainer,
        ON_ERROR_CONTAINER to MaterialTheme.colorScheme.onErrorContainer,
        BACKGROUND to MaterialTheme.colorScheme.background,
        ON_BACKGROUND to MaterialTheme.colorScheme.onBackground,
        PRIMARY to MaterialTheme.colorScheme.primary,
        ON_PRIMARY to MaterialTheme.colorScheme.onPrimary,
        INVERSE_PRIMARY to MaterialTheme.colorScheme.inversePrimary,
        PRIMARY_CONTAINER to MaterialTheme.colorScheme.primaryContainer,
        ON_PRIMARY_CONTAINER to MaterialTheme.colorScheme.onPrimaryContainer,
        SECONDARY to MaterialTheme.colorScheme.secondary,
        ON_SECONDARY to MaterialTheme.colorScheme.onSecondary,
        SECONDARY_CONTAINER to MaterialTheme.colorScheme.secondaryContainer,
        ON_SECONDARY_CONTAINER to MaterialTheme.colorScheme.onSecondaryContainer,
        TERTIARY to MaterialTheme.colorScheme.tertiary,
        ON_TERTIARY to MaterialTheme.colorScheme.onTertiary,
        TERTIARY_CONTAINER to MaterialTheme.colorScheme.tertiaryContainer,
        ON_TERTIARY_CONTAINER to MaterialTheme.colorScheme.onTertiaryContainer,
        OUTLINE to MaterialTheme.colorScheme.outline,
        OUT_LINE_VARIANT to MaterialTheme.colorScheme.outlineVariant,
        SCRIM to MaterialTheme.colorScheme.scrim,
    ).getValue(it)
}

//
internal val sharApp: (Context, String) -> Unit = { ctx, txt ->
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
internal val sharNote: (Context, String, String, then: () -> Unit) -> Unit = { ctx, title, description ,then ->
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
internal val callNumber: (Context,String) -> Unit = { ctx, number ->
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
fun copyNote(
    ctx: Context,
    noteVM:NoteVM,
    note:Note,
    uid:UUID,
    cc : () -> Unit
) {
    noteVM.addNote(
        note.copy(uid = uid.toString())
    )
    //
    "${ctx.filesDir.path}/$IMAGE_FILE/".apply {
        File("$this${note.uid}.$JPEG").let {
            if (it.exists()) it.copyTo(File("${this}${uid}.$JPEG"))
        }
    }
    //
    "${ctx.filesDir.path}/$AUDIO_FILE/".apply {
        File("$this${note.uid}.$MP3").let {
            if (it.exists()) it.copyTo(File("$this${uid}.$MP3"))
        }
    }
    //
    cc.invoke()
}

//
val findUrlLink: (String?) -> String? = {
    it?.split(' ')?.find { str -> str.matches("https?://.+".toRegex()) }
}

//
@JvmName("filterBadWordsString")
fun MutableState<String?>.filterBadWords(): MutableState<String?> {
    value?.split(' ')?.onEach {
        if (
            it.lowercase() in listOfBadEnglishWords
//                .plus(badItian)
//                .plus(badSpanish)
//                .plus(badGerman)
//                .plus(badFrench)
        )
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
        if (
            it.lowercase() in listOfBadEnglishWords
//                .plus(badItian)
//                .plus(badSpanish)
//                .plus(badGerman)
//                .plus(badFrench)
        )
            value = value.replace(
                it,
                "${it.first()}${it.map { '*' }.joinToString("").drop(2)}${it.last()}",
                true
            )
    }
    return this
}

fun MutableState<String?>.filterBadEmoji(): MutableState<String?>{
    listOfBadEmojis.entries.forEach {
        if (this.value?.contains(it.key) == true)
            this.value = this.value!!.replace(it.key,it.value)
    }
    return this
}

//
fun MutableState<String?>.filterBadWebsites():MutableState<String?> {
    findUrlLink(this.value)?.let {
        if (URL(this@filterBadWebsites.value).host in listOfBadWebsites ) {
            this@filterBadWebsites.value = "invalid subject!"
            this
        } else {
            this
        }
    }
    return this
}

//
internal val getPriorityOfColor: (Color) -> String = {
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
internal val getColorOfPriority: (String) -> Color = {
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

internal fun checkIntents(
    intent: Intent,
    ctx: Context,
    navHC: NavHostController,
    scope: CoroutineScope
) {
    intent.apply {
        if (action == Intent.ACTION_SEND && type == "text/plain") {
            getStringExtra(Intent.EXTRA_TEXT) ?.let {
                scope.launch() {
                    navHC.navigate("$ADD_ROUTE/${UUID.randomUUID()}/${codeUrl(it)}")
                }
                Toast.makeText(ctx, it, Toast.LENGTH_SHORT).show()
            }
        }
        if (action == Intent.ACTION_VIEW) {
            Toast.makeText(ctx, "action view", Toast.LENGTH_SHORT).show()
        }
        removeCategory(Intent.CATEGORY_DEFAULT)
        action = null
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

// TODO: Move this code to vm.
 fun urlPreview(
    ctx: Context,
    scope: CoroutineScope?,
    res: String?,
    url: MutableState<String>?,
    title: MutableState<String>?,
    host: MutableState<String>?,
    img: MutableState<String>?
) = res?.let {
    BahaUrlPreview(it, object : IUrlPreviewCallback {
        override fun onComplete(urlInfo: UrlInfoItem) {
            scope?.launch(Dispatchers.IO) {
                urlInfo.apply {
                    title?.value = this.title
//                    description.value = this.description
                    host?.value = URL(this.url).host
                    url?.value = this.url
                    img?.value = this.image
                }
            }
        }

        override fun onFailed(throwable: Throwable) {
            Toast.makeText(ctx, "Can't load link", Toast.LENGTH_SHORT).show()
        }
    })
}

