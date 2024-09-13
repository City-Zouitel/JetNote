package city.zouitel.systemDesign

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommonSwipeItem(
    onClick: () -> Unit,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    content: @Composable RowScope.() -> Unit
    ) {

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            when (state) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onSwipeRight.invoke()
                    true
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onSwipeLeft.invoke()
                    true
                }

                SwipeToDismissBoxValue.Settled -> {
                    false
                }
            }
            false
        }

    )

    SwipeToDismissBox(
        state = state,
        backgroundContent = {
            val backgroundColor = animateColorAsState(
                targetValue = when (state.targetValue) {
                    SwipeToDismissBoxValue.StartToEnd -> Color.Green
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                    SwipeToDismissBoxValue.Settled -> Color.Unspecified
                }, label = ""
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor.value)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = onClick
            ) {
                onClick.invoke()
            },
        content = content
    )
}