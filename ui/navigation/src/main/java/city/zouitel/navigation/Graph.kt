package city.zouitel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import city.zouitel.navigation.about_screen.AppAbout
import city.zouitel.navigation.draw_screen.DrawingNote
import city.zouitel.navigation.home_screen.NoteHome
import city.zouitel.navigation.settings_screen.Licenses
import city.zouitel.navigation.settings_screen.Settings
import city.zouitel.navigation.deleted_screen.TrashScreen
import city.zouitel.note.ui.add_screen.NoteAdd
import city.zouitel.note.ui.edit_screen.NoteEdit
import city.zouitel.systemDesign.Cons.ABOUT_ROUTE
import city.zouitel.systemDesign.Cons.ADD_ROUTE
import city.zouitel.systemDesign.Cons.AUDIO_DURATION
import city.zouitel.systemDesign.Cons.CAMERA_ROUTE
import city.zouitel.systemDesign.Cons.COLOR
import city.zouitel.systemDesign.Cons.DESCRIPTION
import city.zouitel.systemDesign.Cons.DRAW_ROUTE
import city.zouitel.systemDesign.Cons.EDIT_ROUTE
import city.zouitel.systemDesign.Cons.HOME_ROUTE
import city.zouitel.systemDesign.Cons.LICENSES_ROUTE
import city.zouitel.systemDesign.Cons.NON
import city.zouitel.systemDesign.Cons.PRIORITY
import city.zouitel.systemDesign.Cons.REMINDING
import city.zouitel.systemDesign.Cons.SETTING_ROUTE
import city.zouitel.systemDesign.Cons.TAG_ROUTE
import city.zouitel.systemDesign.Cons.TASK_ROUTE
import city.zouitel.systemDesign.Cons.TEXT_COLOR
import city.zouitel.systemDesign.Cons.TITLE
import city.zouitel.systemDesign.Cons.TRASH_ROUTE
import city.zouitel.systemDesign.Cons.UID
import city.zouitel.tags.ui.Tags
import city.zouitel.tasks.TaskList

@Composable
fun Graph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = HOME_ROUTE) {
        composable(route = HOME_ROUTE) {
            NoteHome(navController = navHostController)
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
        composable("$TAG_ROUTE/{$UID}", arguments = listOf(
            navArgument(UID) {
                nullable = true
                type = NavType.StringType
            }
        )) {
            Tags(
                noteUid = it.arguments?.getString(UID) ?: "",
            )
        }
        composable("$TASK_ROUTE/{$UID}", arguments = listOf(
            navArgument(UID) {
                type = NavType.StringType
            }
        )) {
            TaskList(noteUid = it.arguments?.getString(UID) ?: "")
        }
        composable(LICENSES_ROUTE) {
            Licenses()
        }
        composable(ABOUT_ROUTE) {
            AppAbout(navC = navHostController)
        }
    }
}