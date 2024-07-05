package city.zouitel.media.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.AudioUseCase
import city.zouitel.domain.usecase.MediaUseCase
import city.zouitel.domain.usecase.NoteAndMediaUseCase
import city.zouitel.media.mapper.MediaMapper
import city.zouitel.media.model.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MediaScreenModel(
    getAllMedias: MediaUseCase.GetAllMedias,
    private val addMedia: MediaUseCase.AddMedia,
    private val updateMedia: MediaUseCase.UpdateMedia,
    private val deleteMedia: MediaUseCase.DeleteMedia,
    private val mapper: MediaMapper
): ScreenModel {

    private val _allMedias: MutableStateFlow<List<Media>> = MutableStateFlow(
        emptyList()
    )

    val allMedias: StateFlow<List<Media>> = _allMedias.stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            listOf()
        )

    init {
        screenModelScope.launch {
            getAllMedias.invoke().collect { audios -> _allMedias.value = mapper.fromDomain(audios) }
        }
    }

    fun addMedia(media: Media) {
        screenModelScope.launch(Dispatchers.IO) {
            addMedia.invoke(mapper.toDomain(media))
        }
    }

    fun updateMedia(media: Media) {
        screenModelScope.launch {
            updateMedia.invoke(mapper.toDomain(media))
        }
    }

    fun deleteMedia(media: Media) {
        screenModelScope.launch(Dispatchers.IO) {
            deleteMedia.invoke(mapper.toDomain(media))
        }
    }
}