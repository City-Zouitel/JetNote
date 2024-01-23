package city.zouitel.audios.di

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer

//@Module
//@InstallIn(SingletonComponent::class)
object ExoPlayerMod {

//    @Singleton
//    @Provides
    fun buildMediaPlayer(
        /*@ApplicationContext*/ context: Context,
    ) = ExoPlayer.Builder(context).build().apply {
        setHandleAudioBecomingNoisy(true)
    }
}


