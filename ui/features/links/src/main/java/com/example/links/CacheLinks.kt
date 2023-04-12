package com.example.links

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.local.model.Link
import com.example.local.model.NoteAndLink
import kotlin.random.Random

@Composable
fun CacheLinks(
    linkVM: LinkVM,
    noteAndLinkVM: NoteAndLinkVM,
    noteUid: String,
    url: String
) {
    val observeLinks = remember(linkVM, linkVM::getAllLinks).collectAsState()
    val ctx = LocalContext.current

    val title = remember { mutableStateOf("") }
//    val description = remember { mutableStateOf("") }
    val host = remember { mutableStateOf("") }
    val img = remember { mutableStateOf("") }
    val link_id = remember { mutableStateOf(Random.nextLong()) }

    //
    linkVM.urlPreview(
        ctx, url, title, host, img
    )

    if (
        observeLinks.value.none { it.url == url } &&
        title.value.isNotBlank() &&
        host.value.isNotBlank()
    ) {
        linkVM.addLink(
            Link(
                id = link_id.value,
                url = url,
                title = title.value,
                host = host.value
            )
        )
        noteAndLinkVM.addNoteAndLink(
            NoteAndLink(
                noteUid = noteUid,
                linkId = link_id.value
            )
        )
    }
}