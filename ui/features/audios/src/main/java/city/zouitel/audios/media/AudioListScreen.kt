package city.zouitel.audios.media

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun AudioListRoute(
    viewModel: AudioListViewModel = koinViewModel(),
) {

    AudioListScreen(
        uiState = viewModel.uiState,
        onQueryChange = viewModel::updateSearchQuery
    )

}

@Composable
fun AudioListScreen(
    uiState: AudioListUiState,
    onQueryChange: (String) -> Unit,
) {
    AudioList(uiState = uiState, onQueryChange = onQueryChange)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AudioList(
    uiState: AudioListUiState,
    onQueryChange: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        stickyHeader {
            SearchField(value = uiState.searchQuery, onValueChange = onQueryChange)
        }
        items(items = uiState.audioFiles, key = { it.id }) {
            AudioItem(itemState = it)
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
    modifier: Modifier = Modifier,
    itemState: SingleAudioUiState
) {
    Surface(
        tonalElevation = 2.dp,
        onClick = itemState.onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .then(modifier),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(horizontal = 4.dp),
                painter = painterResource(id = city.zouitel.systemDesign.Icons.PLUS_ICON_18),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = itemState.displayName,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = itemState.size,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}