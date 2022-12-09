package com.example.jetnote.ds

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.jetnote.cons.LAYOUT_PREFERENCES_KEY
import com.example.jetnote.cons.ORDER_BY_DEFAULT
import com.example.jetnote.cons.ORDER_PREFERENCES_KEY
import kotlinx.coroutines.flow.map

class DataStore (private val ctx:Context){
    companion object{
        private val Context.layout by preferencesDataStore(LAYOUT_PREFERENCES_KEY)
        val LAYOUT_KEY = booleanPreferencesKey(LAYOUT_PREFERENCES_KEY)

        private val Context.order by preferencesDataStore(ORDER_PREFERENCES_KEY)
        val ORDER_KEY = stringPreferencesKey(ORDER_PREFERENCES_KEY)

        private val Context.dark_theme by preferencesDataStore("APP_THEME_PREFERENCES_KEY")
        val DARK_THEME = booleanPreferencesKey("APP_THEME_PREFERENCES_KEY")
    }

    val getLayout = ctx.layout.data.map { it[LAYOUT_KEY] ?: false }
    suspend fun saveLayout(value:Boolean){
        ctx.layout.edit {
            it[LAYOUT_KEY] = value
        }
    }

    val getOrder = ctx.order.data.map { it[ORDER_KEY] ?: ORDER_BY_DEFAULT }
    suspend fun saveOrder(value:String){
        ctx.order.edit {
            it[ORDER_KEY] = value
        }
    }

    val isDarkTheme = ctx.dark_theme.data.map { it[DARK_THEME] ?: true }
    suspend fun saveTheme(value: Boolean){
        ctx.dark_theme.edit {
            it[DARK_THEME] = value
        }
    }
}





