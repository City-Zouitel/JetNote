package city.zouitel.audio.player

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionToken
import city.zouitel.audio.model.Audio
import city.zouitel.audio.model.Record
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking

class PlaybackManager (
    private val context: Context,
    private val playService: MediaSessionService
): Player.Listener {

    companion object {
        private const val PLAYER_POSITION_UPDATE_TIME = 500L
    }

    val events: MutableSharedFlow<Event> = MutableSharedFlow()

    private var lastEmittedPosition: Long = 0
    private var playlist: MutableList<MediaItem> = mutableListOf() // Use a list for multiple items
    private var currentMediaItem: MediaItem? = null
    private var controllerFuture: ListenableFuture<MediaController>? = null
    private val controller: MediaController?
        get() = if (controllerFuture?.isDone == true) controllerFuture?.get() else null

    private var handler: Handler? = null

    private val playerPositionRunnable = object : Runnable {
            override fun run() {
                val playbackPosition = controller?.currentPosition ?: 0

                // Emit only new player position
                if (playbackPosition != lastEmittedPosition) {
                    sendEvent(Event.PositionChanged(playbackPosition))
                    lastEmittedPosition = playbackPosition
                }
                sendEvent(Event.PlayingChanged(controller?.isPlaying ?: false))
                sendEvent(Event.IsLoading(controller?.isLoading ?: false))
                handler?.postDelayed(this, PLAYER_POSITION_UPDATE_TIME)
            }
    }



    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        sendEvent(Event.CurrentPath(currentMediaItem?.mediaId ?: ""))
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        val item = getMediaItem(currentMediaItem?.mediaId?.toUri() ?: Uri.EMPTY, "")
        val index = playlist.indexOf(item)

        if (index != -1 && mediaItem?.mediaId == currentMediaItem?.mediaId) {
            controller?.seekTo(index, 0)
            controller?.prepare()
            controller?.play()
        } else {
            controller?.stop()
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == Player.STATE_ENDED) {
            controller?.stop()
            controller?.seekToDefaultPosition()
        }
    }

    fun setAudio(audio: Audio) {
        setAudio(audio.uri.toUri(), audio.nameWithoutFormat)
    }

    private fun setAudio(uri: Uri, title: String) {
        setAudio(getMediaItem(uri, title))
    }

    private fun setAudio(mediaItem: MediaItem) {
        val controllerItemId = controller?.currentMediaItem?.mediaId
        currentMediaItem = mediaItem
        if (controllerFuture?.isDone == false || controllerItemId == mediaItem.mediaId) {
            return
        }
        controller?.setMediaItem(mediaItem)
        controller?.prepare()
    }

    private fun clearAudio() {
        controller?.stop()
        controller?.clearMediaItems()
        currentMediaItem = null
    }

    fun addAudio(audio: Audio) {
        addAudio(audio.uri.toUri(), audio.nameWithoutFormat)
    }

    fun addRecord(record: Record) {
        val mediaItem = getMediaItem(record.path.toUri(), record.path)
        playlist.add(mediaItem)
        controller?.addMediaItem(mediaItem)
    }

    fun addRecords(items: List<Record>) {
        val records = items.map { getMediaItem(it.path.toUri(), it.title) }
        playlist.addAll(records)
        controller?.addMediaItems(records)
    }

    private fun addAudio(uri: Uri, title: String) {
        addAudio(getMediaItem(uri, title))
    }

    private fun addAudio(mediaItem: MediaItem) {
        playlist.add(mediaItem)
        controller?.addMediaItem(mediaItem)
    }

    fun addAudios(mediaItems: List<MediaItem>) {
        playlist.addAll(mediaItems)
        controller?.addMediaItems(mediaItems)
    }

    fun removeRecord(record: Record) {
        val mediaItem = getMediaItem(record.path.toUri(), record.title)
        val index = playlist.indexOf(mediaItem)
        if (index != -1) {
            playlist.removeAt(index)
            controller?.removeMediaItem(index)
        }
    }

    fun clearPlaylist() {
        playlist.clear()
        controller?.clearMediaItems()
    }

    fun play() {
        controller?.play()
    }

    fun pause() {
        controller?.pause()
    }

    fun seekTo(position: Long) {
        controller?.seekTo(position)
    }

    fun initializeController() {
        controllerFuture = MediaController.Builder(
            context,
            SessionToken(context, ComponentName(context, playService::class.java))
        ).buildAsync()
        controllerFuture?.addListener(::onControllerCreated, MoreExecutors.directExecutor())
    }

    fun releaseController() {
        clearAudio()
        controller?.removeListener(this)
        controllerFuture?.let(MediaController::releaseFuture)
        controllerFuture = null
        handler?.removeCallbacks(playerPositionRunnable)
        handler = null

        playlist.clear()
        controller?.clearMediaItems()
    }

    private fun getMediaItem(uri: Uri, title: String): MediaItem {
        val mmd = MediaMetadata.Builder()
            .setTitle(title)
            .build()
        val rmd = MediaItem.RequestMetadata.Builder()
            .setMediaUri(uri)
            .build()
        return MediaItem.Builder()
            .setMediaId(uri.toString())
            .setMediaMetadata(mmd)
            .setRequestMetadata(rmd)
            .build()
    }

    private fun onControllerCreated() {
        currentMediaItem?.let(::setAudio)
        // Add all media items to the controller
        controller?.addMediaItems(playlist)
        handler = Handler(Looper.getMainLooper())
        handler?.postDelayed(playerPositionRunnable, PLAYER_POSITION_UPDATE_TIME)
        controller?.addListener(this)
    }

    fun playItem(record: Record) {
        val mediaItem = getMediaItem(record.path.toUri(), record.title)
        val index = playlist.indexOf(mediaItem)
        currentMediaItem = mediaItem

        if (index != -1) {
            controller?.seekTo(
                index,
                if(record.path == currentMediaItem?.mediaId) lastEmittedPosition else 0
            )
            controller?.setMediaItem(mediaItem)
            controller?.prepare()
            controller?.play()
        }
    }

    fun pauseItem(record: Record) {
        if(record.path == currentMediaItem?.mediaId) {
            controller?.pause()
        } else {
            playItem(record)
        }
    }

    private fun sendEvent(event: Event) {
        runBlocking { events.emit(event) }
    }

    sealed interface Event {
        data class PositionChanged(val position: Long) : Event
        data class PlayingChanged(val isPlaying: Boolean) : Event
        data class IsLoading(val isLoading: Boolean): Event
        data class CurrentPath(val path: String): Event
    }
}