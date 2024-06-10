package city.zouitel.audios.state

import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator

internal data class AudioListUiState(
    val searchQuery: String = "",
    val audioFiles: List<SingleAudioUiState> = emptyList(),
    val isLoadingAudios: Boolean = false,
    val currentId: String = "",
    val bottomSheetNavigator: BottomSheetNavigator? = null,
    val navigator: Navigator? = null,
    val newAudio: Boolean = true
)