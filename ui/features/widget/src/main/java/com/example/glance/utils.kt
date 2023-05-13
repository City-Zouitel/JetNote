package com.example.glance

import android.content.Intent
import androidx.core.net.toUri
import androidx.glance.action.Action
import androidx.navigation.NavController
import com.example.local.model.Note

private val baseUri = "app://com.example.mobile".toUri()

internal fun goToNote(navController: NavController, note: Note) {
        "Edit/${note.uid}/${note.title}/${note.description}/${note.color}/${note.textColor}" +
                "/${note.priority}/${note.audioDuration}/${note.reminding}"
}