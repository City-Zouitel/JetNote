package city.zouitel.audios.ui.list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.audios.state.SingleAudioUiState
import city.zouitel.systemDesign.CommonBottomSheet

data class AudioListScreen(val noteId: String): Screen {
    @Composable
    override fun Content() {
        AudioList(audioListModel = getScreenModel())
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun AudioList(audioListModel: AudioListScreenModel) {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val navigator = LocalNavigator.current

        LaunchedEffect(Unit) {
            audioListModel.updateId(noteId)
                .updateBottomSheetNavigator(bottomSheetNavigator)
                .updateNavigator(navigator)
        }

        val uiState by remember(audioListModel, audioListModel::audioListUiState).collectAsState()

        Navigator(
            CommonBottomSheet({
                LazyColumn(
                    modifier = Modifier.animateContentSize()
                ) {
                    items(uiState.audioFiles) {
                        AudioItem(itemState = it, audioListModel = audioListModel)
                    }
                }
            })
        )
    }

    @Composable
    private fun AudioItem(
        itemState: SingleAudioUiState,
        audioListModel: AudioListScreenModel
    ) {
        Surface(
            modifier = Modifier.padding(3.dp),
            color = MaterialTheme.colorScheme.onSurface,
            shape = ShapeDefaults.Large,
            tonalElevation = 0.dp,
            onClick = {
                itemState.onClick.invoke()
                audioListModel.updateNewAudio(false)
            }
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