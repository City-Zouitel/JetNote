package city.zouitel.links.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.*

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CacheLinks(
    linkVM: LinkVM,
    noteAndLinkVM: NoteAndLinkVM,
    noteId: String,
    url: String
) {
    val observeLinks = remember(linkVM, linkVM::getAllLinks).collectAsState()
    val observerNoteAndLinks = remember(noteAndLinkVM, noteAndLinkVM::getAllNotesAndLinks).collectAsState()

    val context = LocalContext.current

    val title = remember { mutableStateOf("") }
//    val description = remember { mutableStateOf("") }
    val host = remember { mutableStateOf("") }
    val img = remember { mutableStateOf("") }
    val id = UUID.randomUUID().toString()

    linkVM.urlPreview(context, url, title, host, img)?.fetchUrlPreview()

    if (
        observeLinks.value.none { it.image == img.value } &&
        title.value.isNotBlank() &&
        host.value.isNotBlank() &&
        img.value.isNotBlank()
    ) {
        linkVM.doWork(
            linkId = id,
            noteId = noteId,
            url = url,
            image = img.value,
            title = title.value,
            host = host.value
        )
    }
}