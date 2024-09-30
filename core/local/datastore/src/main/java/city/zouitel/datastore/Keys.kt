package city.zouitel.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import city.zouitel.datastore.Cons

object Keys {
    val LAYOUT_KEY = stringPreferencesKey(Cons.LAYOUT)

    val ORDINATION_KEY = stringPreferencesKey(Cons.ORDINATION)

    val THEME_KEY = stringPreferencesKey(Cons.THEME)

    val SOUND_KEY = booleanPreferencesKey(Cons.SOUND)
}