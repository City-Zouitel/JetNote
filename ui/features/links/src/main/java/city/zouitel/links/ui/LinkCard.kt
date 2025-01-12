package city.zouitel.links.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import city.zouitel.domain.utils.Action
import city.zouitel.links.model.Link
import city.zouitel.systemDesign.CommonSwipeItem
import coil.compose.AsyncImage
import org.koin.java.KoinJavaComponent.inject

@Composable
fun LinkCard(isSwipe: Boolean, link: Link) {
    val uriHand = LocalUriHandler.current
    val linkModel by inject<LinkScreenModel>(LinkScreenModel::class.java)

    if (isSwipe) {
        CommonSwipeItem(
            onSwipeLeft = {
                linkModel.sendAction(Action.DeleteById(link.id))
            },
            onSwipeRight = {
                uriHand.openUri(link.url)
            }
        ) {
            Link(isSwipe = isSwipe, link = link)
        }
    } else {
        Link(isSwipe = isSwipe, link = link)
    }
}

@Composable
private fun Link(isSwipe: Boolean, link: Link) {
    Card(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
            .padding(if (isSwipe) 8.dp else 0.dp),
        shape = if (isSwipe) RoundedCornerShape(15.dp) else RoundedCornerShape(0.dp),
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
                    text = link.title,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(
                        start = 5.dp,
                        top = 5.dp,
                    )
                )

                Text(
                    text = link.description,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(
                        start = 5.dp,
                        top = 5.dp,
                    )
                )
            }
        }
    }
}