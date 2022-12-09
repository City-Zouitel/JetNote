package com.example.jetnote.ui.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetnote.cons.*
import com.example.jetnote.ui.add_screen.NoteAdd
import com.example.jetnote.ui.camera_screen.LaunchCameraX
import com.example.jetnote.ui.draw_screen.DrawingNote
import com.example.jetnote.ui.edit_screen.NoteEdit
import com.example.jetnote.ui.home_screen.NoteHome
import com.example.jetnote.ui.labels_screen.Labels
import com.example.jetnote.ui.settings_screen.Settings
import com.example.jetnote.ui.todo_list.TodoList
import com.example.jetnote.ui.trash_screen.TrashScreen

@Composable
fun NoteRoot() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HOME_ROUTE) {
        composable(route = HOME_ROUTE) {
            NoteHome(navController = navController)
        }
        composable(route = "$ADD_ROUTE/{$UID}", arguments = listOf(
            navArgument(UID) {
                type = NavType.StringType
            }
        )) {
            NoteAdd(
                navController = navController,
                uid = it.arguments?.getString(UID) ?:""
            )
        }
        composable(
            route = "$EDIT_ROUTE/{$UID}/{$TITLE}/{$DESCRIPTION}/{$COLOR}/{$TEXT_COLOR}/{$PRIORITY}/{$AUDIO_DURATION}/{$REMINDING}",
            arguments = listOf(
                navArgument(UID) {
                    type = NavType.StringType
                },
                navArgument(TITLE) {
                    nullable = true
                    type = NavType.StringType
                },
                navArgument(DESCRIPTION) {
                    nullable = true
                    type = NavType.StringType
                },
                navArgument(COLOR) {
                    type = NavType.IntType
                },
                navArgument(TEXT_COLOR) {
                    type = NavType.IntType
                },
                navArgument(PRIORITY) {
                    type = NavType.StringType
                },
                navArgument(AUDIO_DURATION) {
                    type = NavType.IntType
                },
                navArgument(REMINDING) {
                    type = NavType.LongType
                }
            )
        ) {
            NoteEdit(
                navController = navController,
                title = it.arguments?.getString(TITLE),
                description = it.arguments?.getString(DESCRIPTION),
                color = it.arguments?.getInt(COLOR) ?: 0,
                textColor = it.arguments?.getInt(TEXT_COLOR) ?: 0x0000,
                priority = it.arguments?.getString(PRIORITY) ?: NON,
                uid = it.arguments?.getString(UID) ?: "",
                audioDuration = it.arguments?.getInt(AUDIO_DURATION) ?: 0,
                reminding = it.arguments?.getLong(REMINDING) ?: 0L
            )
        }
        composable("$DRAW_ROUTE/{$TITLE}/{$DESCRIPTION}/{$COLOR}/{$TEXT_COLOR}/{$PRIORITY}/{$UID}/{$AUDIO_DURATION}/{$REMINDING}",
            arguments = listOf(
                navArgument(TITLE) {
                    type = NavType.StringType
                },
                navArgument(DESCRIPTION) {
                    type = NavType.StringType
                },
                navArgument(COLOR) {
                    type = NavType.IntType
                },
                navArgument(TEXT_COLOR) {
                    type = NavType.IntType
                },
                navArgument(PRIORITY) {
                    type = NavType.StringType
                },
                navArgument(UID) {
                    type = NavType.StringType
                },
                navArgument(AUDIO_DURATION) {
                    type = NavType.IntType
                },
                navArgument(REMINDING) {
                    type = NavType.LongType
                }
            )) {
            DrawingNote(
                navController = navController,
                title = it.arguments?.getString(TITLE) ?: "",
                description = it.arguments?.getString(DESCRIPTION) ?: "",
                color = it.arguments?.getInt(COLOR),
                priority = it.arguments?.getString(PRIORITY) ?: NON,
                textColor = it.arguments?.getInt(TEXT_COLOR) ?: 0x00000,
                uid = it.arguments?.getString(UID) ?: "",
                audioDuration = it.arguments?.getInt(AUDIO_DURATION) ?: 0,
                reminding = it.arguments?.getLong(REMINDING) ?: 0L
            )
        }
        composable(route = "$CAMERA_ROUTE/{$UID}", arguments = listOf(
            navArgument(UID){
                type = NavType.StringType
            }
        )){
            LaunchCameraX(uid = it.arguments?.getString(UID))
        }

        composable(TRASH_ROUTE){
            TrashScreen(navController = navController)
        }

        composable(SETTING_ROUTE){
            Settings(navController)
        }
        composable("labels/{$UID}", arguments = listOf(
            navArgument(UID) {
                nullable = true
                type = NavType.StringType
            }
        )) {
            Labels(
                noteUid = it.arguments?.getString(UID) ?: "",
            )
        }
        composable("todo/{$UID}", arguments = listOf(
            navArgument(UID) {
                type = NavType.StringType
            }
        )) {
            TodoList(noteUid = it.arguments?.getString(UID) ?: "")
        }
    }
}