package city.zouitel.screens.settings_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import city.zouitel.systemDesign.CommonIcons

@Composable
fun PreferenceItem(
    title: String,
    description: String?,
    checked: Boolean?,
    onChecked: (Boolean) -> Unit = {},
    onClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClicked)
            .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(.9f),
                text = title,
                fontSize = 23.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            AnimatedVisibility(checked != null) {
                Switch(
                    modifier = Modifier.weight(.1f),
                    checked = checked == true,
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colorScheme.onSurface,
                        checkedThumbColor = MaterialTheme.colorScheme.surface

                    ),
                    onCheckedChange = { onChecked.invoke(it) }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        AnimatedVisibility(description.isNullOrEmpty().not()) {
            Text(
                text = description!!,
                modifier = Modifier.alpha(.5f),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PreferenceItem(
    title: String,
    description: String?,
    items: List<String>,
    currentItem: String,
    onItemClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(.9f),
                text = title,
                fontSize = 23.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(description.isNullOrEmpty().not()) {
            Text(
                text = description!!,
                modifier = Modifier.alpha(.5f),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        ContextualFlowRow(
            modifier = Modifier.animateContentSize(),
            itemCount = items.size
        ) { index ->
            FilledTonalButton(
                modifier = Modifier.padding(2.dp),
                onClick = {
                    onItemClicked.invoke(items[index])
                }
            ) {
                AnimatedVisibility(items[index] == currentItem) {
                    Icon(painter = painterResource(CommonIcons.DONE_ICON_18), null)

                    Spacer(modifier = Modifier.width(10.dp))
                }

                Text(
                    text = items[index],
                    fontSize = 18.sp
                )
            }
        }
    }


}