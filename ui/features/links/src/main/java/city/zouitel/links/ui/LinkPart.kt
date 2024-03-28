package city.zouitel.links.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import city.zouitel.links.model.Link
import city.zouitel.links.model.NoteAndLink as InNoteAndLink
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState

@Composable
fun LinkPart(
    linkVM: LinkVM,
    noteAndLinkVM: NoteAndLinkVM,
    noteUid: String,
    swipeable: Boolean,
    link: Link,
) {
    val uriHand = LocalUriHandler.current
    val swipeState = rememberSwipeableActionsState()

    val action = SwipeAction(
        onSwipe = {
            linkVM.deleteLink(link)
            noteAndLinkVM.deleteNoteAndLink(
                InNoteAndLink(noteUid, link.id)
            )
        },
        icon = {},
        background = Color.Transparent
    )

    if (swipeable) {
        SwipeableActionsBox(
            modifier = Modifier,
            backgroundUntilSwipeThreshold = Color.Transparent,
            endActions = listOf(action),
            swipeThreshold = 100.dp,
            state = swipeState
        ) {
            LinkCard(
                linkVM = linkVM,
                swipeable = swipeable,
                id = link.id,
                title = link.title ?: "",
                host = link.host ?: "",
            ) {
                uriHand.openUri(link.url)
            }
        }
    } else {
        LinkCard(
            linkVM = linkVM,
            swipeable = swipeable,
            id = link.id,
            title = link.title ?: "",
            host = link.host ?: "",
        ) {
            uriHand.openUri(link.url)
        }
    }
}

@Composable
private fun LinkCard(
    linkVM: LinkVM,
    swipeable: Boolean,
    title: String,
    host: String,
    id: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(if (swipeable) 20.dp else 0.dp),
        shape = if (swipeable) RoundedCornerShape(15.dp) else RoundedCornerShape(0.dp),
        onClick = {
            onClick.invoke()
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(.6f, .6f, .6f, .5f)
        )
    ) {
        Row {
            linkVM::imageDecoder.invoke(context, id)?.let {
                Image(
                    modifier = Modifier.size(70.dp),
                    contentScale = ContentScale.Crop,
                    bitmap = it,
                    contentDescription = null
                )
            }
            Column{
                Text(
                    text = title,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(
                        start = 5.dp,
                        top = 10.dp,
                    )
                )

                Text(
                    text = host,
                    fontSize = 11.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(
                        start = 5.dp,
                        top = 10.dp,
                    )
                )
            }
        }
    }
}

