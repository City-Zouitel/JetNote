package city.zouitel.screens.tags_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.CommonIcons.CIRCLE_ICON_18
import city.zouitel.systemDesign.CommonIcons.CROSS_CIRCLE_ICON
import city.zouitel.tags.model.Tag
import city.zouitel.tags.ui.NoteAndTagScreenModel
import city.zouitel.tags.ui.TagScreenModel
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HashTagLayout(
    tagModel: TagScreenModel,
    hashTags: Collection<Tag>,
) {
    FlowRow(mainAxisSpacing = 3.dp) {
        hashTags.forEach { label ->
            ElevatedFilterChip(
                selected = true,
                onClick = {},
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = CIRCLE_ICON_18),
                        contentDescription = null,
                        tint = Color(label.color),
                        modifier = Modifier.size(10.dp)
                    )
                },
                trailingIcon = {
                    Icon(
                        painterResource(CROSS_CIRCLE_ICON),
                        null,
                        modifier = Modifier.clickable {
                            tagModel.deleteTag(label)
                        }
                    )
                },
                label = {
                    label.label?.let {
                        Surface(
                            color = Color.Transparent,
                            modifier = Modifier.combinedClickable(
                                onLongClick = {
                                    tagModel.updateId(label.id)
                                        .updateColorDialogState(true)
                                        .updateLabel(label.label ?: "")
                                        .updateColor(label.color)
                                },
                            ) {
                                tagModel.updateId(label.id)
                                    .updateLabel(label.label ?: "")
                                    .updateColor(label.color)
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
}
