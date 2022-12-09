package com.example.jetnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.example.jetnote.cons.AUDIO_FILE
import com.example.jetnote.cons.IMAGE_FILE
import com.example.jetnote.db.entities.note.Note
import com.example.jetnote.ui.root.AppTheme
import com.example.jetnote.ui.root.NoteRoot
import com.example.jetnote.vm.NoteVM
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*

@AndroidEntryPoint
class NoteActivity : ComponentActivity() {

    val vm = viewModels<NoteVM>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            AppTheme {
                NoteRoot()
//                Main()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.activity.ComponentActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        // TODO: move it to init module as work manager.
        File(this.filesDir.path + "/" + IMAGE_FILE).mkdirs()
        File(this.filesDir.path + "/" + AUDIO_FILE).mkdirs()
//        Toast.makeText(this, "files created!", Toast.LENGTH_SHORT).show()

//        mapOf(
//            "Coffee" to "Prepare hot coffee for my self.",
//            "Certification" to "Call instructor for complete details.",
//            "Team Meeting" to "Planning sprint log for next product application update.",
//            "Birthday Party" to "Tomorrow is my brother birthday there will be party at 7:00 pm.",
//            "Vacation Tickets" to  "Buy tickets for the family vacation.",
//            "Appointment" to "Health check up with physician."
//
//        ).forEach {
//            vm.value.addNote(
//                Note(
//                    uid = UUID.randomUUID().toString(),
//                    title = it.key,
//                    description = it.value
//                )
//            )
//        }
    }
}