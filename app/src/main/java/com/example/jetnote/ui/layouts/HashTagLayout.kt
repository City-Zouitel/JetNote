package com.example.jetnote.ui.layouts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetnote.icons.CIRCLE_ICON_18
import com.example.jetnote.icons.CROSS_CIRCLE_ICON
import com.example.jetnote.db.entities.label.Label
import com.example.jetnote.vm.LabelVM
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HashTagLayout(
    labelVM: LabelVM = hiltViewModel(),
    labelDialogState: MutableState<Boolean>,
    hashTags: Collection<Label>,
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
                            labelVM.deleteLabel(label)
                        }
                    )
                },
                label = {
                    label.label?.let {
                        Surface(
                            color = Color.Transparent,
                            modifier = Modifier.combinedClickable(
                                onLongClick = {
                                    labelState.value = label.label
                                    idState.value = label.id
                                    labelDialogState.value = true
                                },
                            ){
                                labelState.value = label.label
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
