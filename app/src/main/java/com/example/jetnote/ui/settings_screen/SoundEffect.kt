package com.example.jetnote.ui.settings_screen

import android.content.Context
import android.media.AudioManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.example.jetnote.ds.DataStore
import kotlinx.coroutines.Job

val Any.makeSound: (ctx: Context, effectType: Int, thereIsSoundEffect: Boolean) -> Unit
    get() = { ctx, type, sound ->
        val sysServ = ctx.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        if (sound) sysServ.playSoundEffect(type, 5.0f)
    }