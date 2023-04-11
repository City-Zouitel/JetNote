package com.example.links

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.common_ui.Icons
import com.example.local.model.Link
import com.example.local.model.NoteAndLink
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun LinkPart(
    linkVM: LinkVM = hiltViewModel(),
    noteAndLinkVM: NoteAndLinkVM = hiltViewModel(),
    link: String?,
    swipeable: Boolean,
    onSwipe: () -> Unit
) {
    val ctx = LocalContext.current
    val uriHand = LocalUriHandler.current
    val swipeState = rememberSwipeableActionsState()

    val action = SwipeAction(
        onSwipe = {
            onSwipe.invoke()
        },
        icon = {},
        background = Color.Transparent
    )

    val title = remember { mutableStateOf("") }
//    val description = remember { mutableStateOf("") }
    val host = remember { mutableStateOf("") }
    val url = remember { mutableStateOf("") }
    val img = remember { mutableStateOf("") }

    linkVM.urlPreview(
        ctx, link, url, title, host, img
    ).invokeOnCompletion {
        linkVM.addLink(
            Link(
                link = url.value,
                title = title.value,
                host = host.value
            )
        ).invokeOnCompletion {
            noteAndLinkVM.addNoteAndLink(
                NoteAndLink(
                    noteUid = "",
                    linkId = 0L
                )
            )
        }
    }

    if (swipeable) {
        SwipeableActionsBox(
            modifier = Modifier,
            backgroundUntilSwipeThreshold = Color.Transparent,
            endActions = listOf(action),
            swipeThreshold = 100.dp,
            state = swipeState
        ) {
            LinkCard(
                swipeable = swipeable,
                title = title.value,
                host = host.value,
                image = img.value
            ) {
                uriHand.openUri(url.value)
            }
        }
    } else {
        LinkCard(
            swipeable = swipeable,
            title = title.value,
            host = host.value,
            image = img.value
        ) {
            uriHand.openUri(url.value)
        }
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalGlideComposeApi::class
)
@Composable
private fun LinkCard(
    swipeable: Boolean,
    title: String,
    host: String,
    image: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(if (swipeable) 20.dp else 0.dp),
        shape = if (swipeable) roundedForCard else RoundedCornerShape(15.dp),
        onClick = {
            onClick.invoke()
        }
    ) {
        Row {
            GlideImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            ) {
                it.placeholder(Icons.GLOBE_ICON)
            }

            Column {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 5.dp)
                )
                Text(
                    text = host,
                    fontSize = 11.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

private val roundedForCard = RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp)
