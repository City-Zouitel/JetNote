package city.zouitel.audios.state

data class AudioListUiState(
    val searchQuery: String = "",
    val audioFiles: List<SingleAudioUiState> = emptyList(),
    val isLoadingAudios: Boolean = false
)