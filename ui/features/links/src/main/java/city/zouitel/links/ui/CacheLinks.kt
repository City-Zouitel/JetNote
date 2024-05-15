package city.zouitel.links.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import city.zouitel.logic.asShortToast
import java.util.*

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CacheLinks(
    linkScreenModel: LinkScreenModel,
    noteAndLinkScreenModel: NoteAndLinkScreenModel,
    noteId: String,
    url: String
) {
    val observeLinks = remember(linkScreenModel, linkScreenModel::getAllLinks).collectAsState()
    val observerNoteAndLinks = remember(noteAndLinkScreenModel, noteAndLinkScreenModel::getAllNotesAndLinks).collectAsState()

    val context = LocalContext.current

    val title = remember { mutableStateOf("") }
//    val description = remember { mutableStateOf("") }
    val host = remember { mutableStateOf("") }
    val img = remember { mutableStateOf("") }
    val id = Random().nextLong()

    runCatching {
        linkScreenModel.urlPreview(context, url, title, host, img)?.fetchUrlPreview()
    }.onSuccess {
        if (
            observeLinks.value.none { it.image == img.value } &&
            title.value.isNotBlank() &&
            host.value.isNotBlank() &&
            img.value.isNotBlank()
        ) {
            linkScreenModel.doWork(
                linkId = id,
                noteId = noteId,
                url = url,
                image = img.value,
                title = title.value,
                host = host.value
            )
        }
    }.onFailure {
        context.apply {
            it.message ?.asShortToast()
        }
    }
}