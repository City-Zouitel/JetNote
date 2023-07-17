package com.example.links.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.links.LinkStates
import java.util.*

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CacheLinks(
    linkVM: LinkVM,
    noteAndLinkVM: NoteAndLinkVM,
    noteUid: String,
    url: String
) {
//    val observeLinks = remember(linkVM, linkVM::getAllLinks).collectAsState()
    val links = LinkStates.Link(linkVM).rememberAllLinks

    val title = remember { mutableStateOf("") }
    val host = remember { mutableStateOf("") }
    val img = remember { mutableStateOf("") }
    val link_id = UUID.randomUUID().toString()

    //
    linkVM.urlPreview(
        url, title, host, img
    )

    if (
        links.none { it.image == img.value } &&
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
    }
}