package city.zouitel.links.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import city.zouitel.links.model.Link
import coil.compose.AsyncImage
import city.zouitel.links.model.NoteAndLink as InNoteAndLink
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState

@Composable
fun LinkCard(
    linkScreenModel: LinkScreenModel,
    noteAndLinkScreenModel: NoteAndLinkScreenModel,
    noteUid: String,
    isSwipe: Boolean,
    link: Link,
) {
    val uriHand = LocalUriHandler.current
    val swipeState = rememberSwipeableActionsState()

    val action = SwipeAction(
        onSwipe = {
            linkScreenModel.deleteLink(link)
            noteAndLinkScreenModel.deleteNoteAndLink(
                InNoteAndLink(noteUid, link.id)
            )
        },
        icon = {},
        background = Color.Transparent
    )

    if (isSwipe) {
        SwipeableActionsBox(
            modifier = Modifier,
            backgroundUntilSwipeThreshold = Color.Transparent,
            endActions = listOf(action),
            swipeThreshold = 100.dp,
            state = swipeState
        ) {
            LinkCard(
                swipeable = isSwipe,
            link = link
            ) {
                uriHand.openUri(link.url)
            }
        }
    } else {
        LinkCard(
            swipeable = isSwipe,
            link = link
        ) {
            uriHand.openUri(link.url)
        }
    }
}

@Composable
private fun LinkCard(
    swipeable: Boolean,
    link: Link,
    onClick: () -> Unit
) {
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
            AsyncImage(
                modifier = Modifier.size(74.dp),
                model = link.image,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column {
                Text(
                    text = link.title ?: "Unknown",
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(
                        start = 5.dp,
                        top = 10.dp,
                    )
                )

                Text(
                    text = link.host,
                    fontSize = 11.sp,
                    maxLines = 1,
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

