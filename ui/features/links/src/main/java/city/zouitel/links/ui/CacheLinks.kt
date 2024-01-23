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
    noteUid: String,
    url: String
) {
    val observeLinks = remember(linkVM, linkVM::getAllLinks).collectAsState()
    val observerNoteAndLinks =
        remember(noteAndLinkVM, noteAndLinkVM::getAllNotesAndLinks).collectAsState()

    val ctx = LocalContext.current
    val linkImgPath = ctx.filesDir.path + "/" + "links_img"
    val scope = rememberCoroutineScope()

    val title = remember { mutableStateOf("") }
//    val description = remember { mutableStateOf("") }
    val host = remember { mutableStateOf("") }
    val img = remember { mutableStateOf("") }
    val link_id = UUID.randomUUID().toString()

    //
    linkVM.urlPreview(
        url, title, host, img
    )

    if (
        observeLinks.value.none { it.image == img.value } &&
        title.value.isNotBlank() &&
        host.value.isNotBlank() &&
        img.value.isNotBlank()
    ) {

        linkVM.doWork(
            linkId = link_id,
            noteId = noteUid,
            url = url,
            image = img.value,
            title = title.value,
            host = host.value
        )

//        linkVM.addLink(
//            Link(
//                id = link_id,
//                url = url,
//                image = img.value,
//                title = title.value,
//                host = host.value
//            )
//        )
//        noteAndLinkVM.addNoteAndLink(
//            NoteAndLink(
//                noteUid = noteUid,
//                linkId = link_id
//            )
//        )

//        scope.launch(Dispatchers.IO) {
//            // save link image in local link images file.
//            val ss = ImageLoader(ctx)
//            val cc = ImageRequest.Builder(ctx)
//                .data(img.value)
//                .target {
//                    linkVM.saveImageLocally(
//                        img = it.toBitmap(),
//                        path = linkImgPath,
//                        name = "$link_id.$JPEG"
//                    )
//                }
//                .build()
//            ss.enqueue(cc)
//        }
    }
}