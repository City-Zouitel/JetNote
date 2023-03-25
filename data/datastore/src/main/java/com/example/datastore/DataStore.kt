package com.example.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastore.Cons.LAYOUT
import com.example.datastore.Cons.ORDINATION
import com.example.datastore.Cons.SOUND_AFFECTION
import com.example.datastore.Cons.THEME
import kotlinx.coroutines.flow.map

class DataStore (private val ctx:Context){
    companion object{
        private val Context.layout by preferencesDataStore(LAYOUT)
        val LAYOUT_KEY = booleanPreferencesKey(LAYOUT)

        private val Context.order by preferencesDataStore(ORDINATION)
        val ORDER_KEY = stringPreferencesKey(ORDINATION)

        private val Context.dark_theme by preferencesDataStore(THEME)
        val THEME_KEY = booleanPreferencesKey(THEME)

        private val Context.soundEffect by preferencesDataStore(SOUND_AFFECTION)
        val SOUND_KEY = booleanPreferencesKey(SOUND_AFFECTION)
    }

    val getLayout = ctx.layout.data.map { it[LAYOUT_KEY] ?: false }
    suspend fun saveLayout(value:Boolean){
        ctx.layout.edit {
            it[LAYOUT_KEY] = value
        }
    }

    val getOrder = ctx.order.data.map { it[ORDER_KEY] ?: ORDINATION }
    suspend fun saveOrder(value:String){
        ctx.order.edit {
            it[ORDER_KEY] = value
        }
    }

    val isDarkTheme = ctx.dark_theme.data.map { it[THEME_KEY] ?: true }
    suspend fun saveTheme(value: Boolean){
        ctx.dark_theme.edit {
            it[THEME_KEY] = value
        }
    }

    val thereIsSoundEffect = ctx.soundEffect.data.map { it[SOUND_KEY] ?: false }
    suspend fun saveSoundEffect(value: Boolean) {
        ctx.soundEffect.edit {
            it[SOUND_KEY] = value
        }
    }
}





