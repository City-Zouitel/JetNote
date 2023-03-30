package com.example.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Keys {
    val LAYOUT_KEY = stringPreferencesKey(Cons.LAYOUT)

    val ORDINATION_KEY = stringPreferencesKey(Cons.ORDINATION)

    val THEME_KEY = stringPreferencesKey(Cons.THEME)

    val SOUND_KEY = booleanPreferencesKey(Cons.SOUND)
}