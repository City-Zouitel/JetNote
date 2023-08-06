package com.example.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import city.zouitel.network.NetworkMonitor
import com.example.common_ui.Cons.ADD_ROUTE
import com.example.common_ui.Cons.AUDIO_DURATION
import com.example.common_ui.Cons.CAMERA_ROUTE
import com.example.common_ui.Cons.COLOR
import com.example.common_ui.Cons.DESCRIPTION
import com.example.common_ui.Cons.DRAW_ROUTE
import com.example.common_ui.Cons.EDIT_ROUTE
import com.example.common_ui.Cons.HOME_ROUTE
import com.example.common_ui.Cons.NON
import com.example.common_ui.Cons.PRIORITY
import com.example.common_ui.Cons.REMINDING
import com.example.common_ui.Cons.SETTING_ROUTE
import com.example.common_ui.Cons.TEXT_COLOR
import com.example.common_ui.Cons.TITLE
import com.example.common_ui.Cons.TRASH_ROUTE
import com.example.common_ui.Cons.UID
import com.example.graph.about_screen.AppAbout
import com.example.graph.draw_screen.DrawingNote
import com.example.graph.home_screen.NoteHome
import com.example.graph.settings_screen.Licenses
import com.example.graph.settings_screen.Settings
import com.example.graph.trash_screen.TrashScreen
import com.example.note.ui.add_screen.NoteAdd
import com.example.note.ui.edit_screen.NoteEdit
import com.example.tags.ui.Tags
import com.example.tasks.TaskList

@Composable
fun Navigation(
    navHostController: NavHostController,
    networkMonitor: city.zouitel.network.NetworkMonitor
) {

    NavHost(navController = navHostController, startDestination = HOME_ROUTE) {
        composable(route = HOME_ROUTE) {
            NoteHome(
                navController = navHostController,
                networkMonitor = networkMonitor)
        }
        composable(
            route = "$ADD_ROUTE/{$UID}/{$DESCRIPTION}",
            arguments = listOf(
            navArgument(UID) {
                type = NavType.StringType
            },
            navArgument(DESCRIPTION) {
                nullable = true
                type = NavType.StringType
            }
        )) {
            NoteAdd(
                navController = navHostController,
                uid = it.arguments?.getString(UID) ?:"",
                description = it.arguments?.getString(DESCRIPTION) ?: ""
            )
        }
        composable(
            route = "$EDIT_ROUTE/{$UID}/{$TITLE}/{$DESCRIPTION}/{$COLOR}/{$TEXT_COLOR}/" +
                    "{$PRIORITY}/{$AUDIO_DURATION}/{$REMINDING}",
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
                navController = navHostController,
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
        composable("$DRAW_ROUTE/{$TITLE}/{$DESCRIPTION}/{$COLOR}/{$TEXT_COLOR}/" +
                "{$PRIORITY}/{$UID}/{$AUDIO_DURATION}/{$REMINDING}",
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
                navController = navHostController,
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
//            LaunchCameraX(uid = it.arguments?.getString(UUID))
        }

        composable(TRASH_ROUTE){
            TrashScreen(navController = navHostController)
        }

        composable(SETTING_ROUTE){
            Settings(navC = navHostController)
        }
        composable("tagEntities/{$UID}", arguments = listOf(
            navArgument(UID) {
                nullable = true
                type = NavType.StringType
            }
        )) {
            Tags(
                noteUid = it.arguments?.getString(UID) ?: "",
            )
        }
        composable("todo/{$UID}", arguments = listOf(
            navArgument(UID) {
                type = NavType.StringType
            }
        )) {
            TaskList(noteUid = it.arguments?.getString(UID) ?: "")
        }
        composable("licenses") {
            Licenses()
        }
        composable("about") {
            AppAbout(navC = navHostController)
        }
    }
}