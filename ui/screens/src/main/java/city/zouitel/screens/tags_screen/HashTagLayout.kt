package city.zouitel.screens.tags_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.CommonIcons.CIRCLE_ICON_18
import city.zouitel.systemDesign.CommonIcons.CROSS_CIRCLE_ICON
import city.zouitel.tags.ui.TagScreenModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
internal fun HashTagLayout(
    tagModel: TagScreenModel,
) {
    val observeAllTags by remember(tagModel, tagModel::getAllLTags).collectAsState()

    ContextualFlowRow(
        modifier = Modifier.animateContentSize(),
        itemCount = observeAllTags.size
    ) { index ->
        ElevatedFilterChip(
            modifier = Modifier.padding(1.5.dp),
            selected = true,
            onClick = {},
            leadingIcon = {
                Icon(
                    painter = painterResource(id = CIRCLE_ICON_18),
                    contentDescription = null,
                    tint = Color(observeAllTags[index].color),
                    modifier = Modifier.size(10.dp)
                )
            },
            trailingIcon = {
                Icon(
                    painterResource(CROSS_CIRCLE_ICON),
                    null,
                    modifier = Modifier.clickable {
                        tagModel.deleteTag(observeAllTags[index])
                    }
                )
            },
            label = {
                observeAllTags[index].label?.let {
                    Surface(
                        color = Color.Transparent,
                        modifier = Modifier.combinedClickable(
                            onLongClick = {
                                tagModel.updateId(observeAllTags[index].id)
                                    .updateColorDialogState(true)
                                    .updateLabel(observeAllTags[index].label ?: "")
                                    .updateColor(observeAllTags[index].color)
                            },
                        ) {
                            tagModel.updateId(observeAllTags[index].id)
                                .updateLabel(observeAllTags[index].label ?: "")
                                .updateColor(observeAllTags[index].color)
                        }
                    ) {
                        Text(it)
                    }
                }
            },
            shape = CircleShape
        )
    }
}