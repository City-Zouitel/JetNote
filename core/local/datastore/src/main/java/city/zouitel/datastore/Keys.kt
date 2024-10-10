package city.zouitel.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Keys {
    val LAYOUT_KEY = stringPreferencesKey(Constants.LAYOUT)
    val ORDINATION_KEY = stringPreferencesKey(Constants.ORDINATION)
    val THEME_KEY = stringPreferencesKey(Constants.THEME)
    val SOUND_KEY = booleanPreferencesKey(Constants.SOUND)
    val LOCK_MODE_KEY = booleanPreferencesKey(Constants.LOCK_MODE)
    val SCREENSHOT_BLOCK_KEY = booleanPreferencesKey(Constants.SCREENSHOT_BLOCK  )
}