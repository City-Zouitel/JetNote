package city.zouitel.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import city.zouitel.datastore.Constants.GEMINI_API
import city.zouitel.datastore.Constants.LAYOUT
import city.zouitel.datastore.Constants.LOCK_MODE
import city.zouitel.datastore.Constants.MUTE
import city.zouitel.datastore.Constants.ORDINATION
import city.zouitel.datastore.Constants.SCREENSHOT_BLOCK
import city.zouitel.datastore.Constants.THEME

object Keys {
    val LAYOUT_KEY = stringPreferencesKey(LAYOUT)
    val ORDINATION_KEY = stringPreferencesKey(ORDINATION)
    val THEME_KEY = stringPreferencesKey(THEME)
    val MUTE_KEY = booleanPreferencesKey(MUTE)
    val LOCK_MODE_KEY = booleanPreferencesKey(LOCK_MODE)
    val SCREENSHOT_BLOCK_KEY = booleanPreferencesKey(SCREENSHOT_BLOCK)
    val GEMINI_API_KEY = stringPreferencesKey(GEMINI_API)
}