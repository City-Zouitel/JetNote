package city.zouitel.tags.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.Icons.CIRCLE_ICON_18
import city.zouitel.systemDesign.Icons.CROSS_CIRCLE_ICON
import city.zouitel.tags.viewmodel.TagViewModel
import city.zouitel.tags.model.Tag as InTag
import com.google.accompanist.flowlayout.FlowRow
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun HashTagLayout(
    tagViewModel: TagViewModel = koinViewModel(),
    labelDialogState: MutableState<Boolean>,
    hashTags: Collection<InTag>,
    idState: MutableState<Long>,
    labelState: MutableState<String>
    ) {
    FlowRow(
        mainAxisSpacing = 3.dp
    ) {
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
                            tagViewModel.deleteTag(label)
                        }
                    )
                },
                label = {
                    label.label?.let {
                        Surface(
                            color = Color.Transparent,
                            modifier = Modifier.combinedClickable(
                                onLongClick = {
                                    labelState.value = label.label!!
                                    idState.value = label.id
                                    labelDialogState.value = true
                                },
                            ){
                                labelState.value = label.label!!
                                idState.value = label.id
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
