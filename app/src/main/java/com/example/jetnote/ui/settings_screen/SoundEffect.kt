package com.example.jetnote.ui.settings_screen

import android.content.Context
import android.media.AudioManager

val Any.makeSound: (ctx: Context, effectType: Int, thereIsSoundEffect: Boolean) -> Unit
    get() = { ctx, type, sound ->
        val sysServ = ctx.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        if (sound) sysServ.playSoundEffect(type, 5.0f)
    }