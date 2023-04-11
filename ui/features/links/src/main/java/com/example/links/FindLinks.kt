package com.example.links

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.local.model.Link
import com.example.local.model.NoteAndLink

@Composable
fun FindLinks(

    link: String?
) {
    val scope = rememberCoroutineScope()
    val uriHand = LocalUriHandler.current



}