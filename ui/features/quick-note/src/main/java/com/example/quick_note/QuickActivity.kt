package com.example.quick_note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels


class QuickActivity: ComponentActivity() {
    val vm = viewModels<QuickNoteVM>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          Quick() {
              finish()
          }
        }
    }
}

