package city.zouitel.audios.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.audios.state.SingleAudioUiState

data class AudioListScreen(val noteId: String): Screen {
    @Composable
    override fun Content() {

        AudioList(audioListModel = getScreenModel<AudioListScreenModel>())
    }

    @Composable
    private fun AudioList(audioListModel: AudioListScreenModel) {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        LaunchedEffect(Unit) {
            audioListModel.updateId(noteId)
                .updateBottomSheetNavigator(bottomSheetNavigator)
        }

        val uiState by remember(audioListModel, audioListModel::audioListUiState).collectAsState()

        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface),
            contentPadding = PaddingValues(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(start = 155.dp, end = 155.dp, top = 12.dp)
                        .clip(ShapeDefaults.Small)
                        .alpha(.5f),
                    thickness = 5.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(items = uiState.audioFiles, key = { it.id }) {
                AudioItem(itemState = it)
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SearchField(
        modifier: Modifier = Modifier,
        value: String,
        onValueChange: (String) -> Unit,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = "search in ..") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            colors = TextFieldDefaults.textFieldColors()
        )
    }

    @Composable
    private fun AudioItem(
        itemState: SingleAudioUiState
    ) {
        Surface(
            color = MaterialTheme.colorScheme.onSurface,
            shape = ShapeDefaults.Large,
            tonalElevation = 0.dp,
            onClick = itemState.onClick
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSurface),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    painter = painterResource(id = city.zouitel.systemDesign.CommonIcons.CASSETTE_ICON),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surface
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = itemState.displayName,
                        color = MaterialTheme.colorScheme.surface
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = itemState.size,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}