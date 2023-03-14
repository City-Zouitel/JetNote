package com.example.glance.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import com.example.glance.model.GlanceNote
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.File
import java.io.InputStream
import java.io.OutputStream

object NoteStateDefinition: GlanceStateDefinition<List<GlanceNote>> {
    private const val STORE_NAME = "widget_notes"

    private val Context.datastore by dataStore(STORE_NAME, NotesSerializer)
    override suspend fun getDataStore(
        context: Context,
        fileKey: String
    ): DataStore<List<GlanceNote>> {
        return context.datastore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(STORE_NAME)
    }

    object NotesSerializer : Serializer<List<GlanceNote>> {
        override val defaultValue: List<GlanceNote>
            get() = persistentListOf()

        override suspend fun readFrom(input: InputStream): List<GlanceNote> {
            return Json.decodeFromStream(input)
        }

        override suspend fun writeTo(t: List<GlanceNote>, output: OutputStream) {
            Json.encodeToStream(t, output)
        }

    }
}