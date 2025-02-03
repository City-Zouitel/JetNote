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
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.domain.utils.Action
import city.zouitel.systemDesign.CommonIcons.CIRCLE_ICON_18
import city.zouitel.systemDesign.CommonIcons.CROSS_CIRCLE_ICON
import city.zouitel.systemDesign.CommonPopupTip
import city.zouitel.tags.model.Tag
import city.zouitel.tags.ui.NoteAndTagScreenModel
import city.zouitel.tags.ui.TagScreenModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
internal fun HashTagLayout(tagModel: TagScreenModel, noteAndTagModel: NoteAndTagScreenModel) {
    val observeAll by remember(tagModel, tagModel::observeAll).collectAsState()
    val uiState by remember(tagModel, tagModel::uiState).collectAsState()

    ContextualFlowRow(
        modifier = Modifier.animateContentSize(),
        itemCount = observeAll.size
    ) { index ->
        ElevatedFilterChip(
            modifier = Modifier.padding(1.5.dp),
            selected = true,
            onClick = {},
            leadingIcon = {
                Icon(
                    painter = painterResource(id = CIRCLE_ICON_18),
                    contentDescription = null,
                    tint = Color(observeAll.getOrNull(index)?.color ?: 0),
                    modifier = Modifier.size(10.dp)
                )
            },
            trailingIcon = {
                Icon(
                    painterResource(CROSS_CIRCLE_ICON),
                    null,
                    modifier = Modifier.clickable {
                        tagModel.sendAction(Action.DeleteById(observeAll.getOrNull(index)?.id ?: 0))
                        noteAndTagModel.sendAction(Action.DeleteById(observeAll.getOrNull(index)?.id ?: 0))
                    }
                )
            },
            label = {
                CommonPopupTip(
                    color = {
                        tagModel.sendAction(
                            Action.Insert(
                                data = Tag(
                                    id = uiState.currentId,
                                    label = uiState.currentLabel,
                                    color = it
                                )
                            )
                        )
                        tagModel
                            .updateId()
                            .updateColorDialogState()
                            .updateColor()
                            .updateLabel()

                    }
                ) { window ->
                    Surface(
                        color = Color.Transparent,
                        modifier = Modifier.combinedClickable(
                            onLongClick = {
                                tagModel.updateId(observeAll.getOrNull(index)?.id ?: 0)
                                    .updateLabel(observeAll.getOrNull(index)?.label ?: "")
                                    .updateColor(observeAll.getOrNull(index)?.color ?: 0)
                                window.showAlignTop()
                            },
                        ) {
                            tagModel.updateId(observeAll.getOrNull(index)?.id ?: 0)
                                .updateLabel(observeAll.getOrNull(index)?.label ?: "")
                                .updateColor(observeAll.getOrNull(index)?.color ?: 0)
                        }
                    ) {
                        Text(observeAll.getOrNull(index)?.label ?: "")
                    }
                }
            },
            shape = CircleShape
        )
    }
}