package com.example.jetnote.vm

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnote.reposImp.ExoRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MediaPlayerVM @Inject constructor(
    private val exoBuilder : ExoRepoImp,
):ViewModel() {

    var getMediaDuration = mutableStateOf(0L)
    private set

    fun playMedia(mediaUri: String) {
        viewModelScope.launch {
            exoBuilder.prepareMediaPlayer(mediaUri).play()
        }
    }

    fun pauseMedia(mediaUri: String) {
        viewModelScope.launch {
            exoBuilder.prepareMediaPlayer(mediaUri).pause()
        }
    }

    fun playStreamMedia(mediaUri: String) {
        viewModelScope.launch {
            exoBuilder.prepareStreamMediaPlayer(mediaUri).play()
        }
    }
    fun pauseStreamMedia(mediaUri: String) {
        viewModelScope.launch {
            exoBuilder.prepareStreamMediaPlayer(mediaUri).pause()
        }
    }

    fun getMediaDuration(context: Context, path: String):Long {
        viewModelScope.launch {
            getMediaDuration.value = exoBuilder.getMediaDuration(context,path)
        }
        return getMediaDuration.value
    }

    internal fun formatLong(value: Long): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(value)
    }
}