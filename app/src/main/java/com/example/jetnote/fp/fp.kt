package com.example.jetnote.fp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.jetnote.cons.*
import com.example.jetnote.db.entities.note.Note
import com.example.jetnote.db.entities.note_and_label.NoteAndLabel
import com.example.jetnote.di.utils.Dispatcher
import com.example.jetnote.di.utils.Dispatchers.*
import com.example.jetnote.vm.LabelVM
import com.example.jetnote.vm.NoteAndLabelVM
import com.example.jetnote.vm.NoteVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

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
val getPriorityColor :(String) -> Color = {
    when(it) {
        URGENT -> Color.Red
        IMPORTANT -> Color.Yellow
        NORMAL -> Color.Green
        LOW -> Color.Cyan
        else -> Color.Transparent
    }
}

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