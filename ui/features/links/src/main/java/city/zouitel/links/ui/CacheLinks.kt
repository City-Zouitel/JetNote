package city.zouitel.links.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import city.zouitel.links.worker.LinkWorker
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
    val observerNoteAndLinks =
        remember(noteAndLinkVM, noteAndLinkVM::getAllNotesAndLinks).collectAsState()

    val ctx = LocalContext.current
    val linkImgPath = ctx.filesDir.path + "/" + "links_img"
    val scope = rememberCoroutineScope()

    val title = remember { mutableStateOf("") }
//    val description = remember { mutableStateOf("") }
    val host = remember { mutableStateOf("") }
    val img = remember { mutableStateOf("") }
    val id = UUID.randomUUID().toString()

    //
    linkVM.urlPreview(
        ctx, url, title, host, img
    )

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