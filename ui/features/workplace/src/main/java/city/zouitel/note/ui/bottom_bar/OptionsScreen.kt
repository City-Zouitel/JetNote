package city.zouitel.note.ui.bottom_bar

import android.Manifest.permission
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.audios.ui.list.AudioListScreen
import city.zouitel.note.ui.utils.PriorityColorsList.HIG
import city.zouitel.note.ui.utils.PriorityColorsList.LOW
import city.zouitel.note.ui.utils.PriorityColorsList.MED
import city.zouitel.note.ui.utils.PriorityColorsList.NON
import city.zouitel.note.ui.utils.PriorityColorsList.URG
import city.zouitel.note.ui.utils.RationalScreen
import city.zouitel.note.ui.workplace.WorkplaceScreenModel
import city.zouitel.recoder.ui.RecorderScreen
import city.zouitel.reminder.ui.ReminderScreen
import city.zouitel.systemDesign.CommonBottomSheet
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonIcons.ADD_IMAGE_ICON
import city.zouitel.systemDesign.CommonIcons.BELL_ICON
import city.zouitel.systemDesign.CommonIcons.CASSETTE_ICON
import city.zouitel.systemDesign.CommonIcons.LIST_CHECK_ICON
import city.zouitel.systemDesign.CommonIcons.TAGS_ICON
import city.zouitel.systemDesign.CommonOptionItem
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.SoundEffect
import city.zouitel.tags.ui.TagsScreen
import city.zouitel.tasks.ui.TasksScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class OptionsScreen(
    val id: String,
    val titleState: TextFieldState?,
    val descriptionState: TextFieldState?,
    val priorityState: MutableState<String>,
    val imageLaunch: ManagedActivityResultLauncher<PickVisualMediaRequest, List<@JvmSuppressWildcards Uri>>,
): Screen {
    @Composable
    override fun Content() {
        Options(
            dataStoreModel = getScreenModel(),
            workspaceModel = getScreenModel()
        )
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun Options(
        dataStoreModel: DataStoreScreenModel,
        workspaceModel: WorkplaceScreenModel
    ) {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val navBottomSheet = LocalBottomSheetNavigator.current

        val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::isMute).collectAsState()
        val uiState by remember(workspaceModel, workspaceModel::uiState).collectAsState()
        val scope = rememberCoroutineScope()

        val sound by lazy { SoundEffect() }

        val recorderPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberMultiplePermissionsState(
                permissions = listOf(
                    permission.RECORD_AUDIO,
                    permission.READ_MEDIA_AUDIO
                )
            ) {
                if (it.all { true }) {
                    navBottomSheet.show(RecorderScreen(id) {})
                }
            }
        } else {
            rememberMultiplePermissionsState(
                permissions = listOf(
                    permission.RECORD_AUDIO,
                )
            ) {
                if (it.all { true }) {
                    navBottomSheet.show(RecorderScreen(id) {})
                }
            }
        }
        val readMediaPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberMultiplePermissionsState(
                permissions = listOf(
                    permission.READ_MEDIA_AUDIO
                )
            ) {
                if (it.all { true }) {
                    navBottomSheet.show(AudioListScreen(id))
                }
            }
        } else {
            rememberMultiplePermissionsState(
                permissions = emptyList()
            ) {
                navBottomSheet.show(AudioListScreen(id))
            }
        }
        val reminderPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberMultiplePermissionsState(
                permissions = listOf(
                    permission.POST_NOTIFICATIONS,
                )
            ) {
                navBottomSheet.show(
                    ReminderScreen(
                        uid = id,
                        title = titleState?.text.toString(),
                        message = descriptionState?.text.toString()
                    )
                )
            }
        } else {
            rememberMultiplePermissionsState(
                permissions = listOf()
            ) {
                navBottomSheet.show(
                    ReminderScreen(
                        uid = id,
                        title = titleState?.text.toString(),
                        message = descriptionState?.text.toString()
                    )
                )
            }
        }

        Navigator(CommonBottomSheet {
            LazyColumn {
                item {
                    CommonOptionItem(
                        name = "Add Image",
                        icon = ADD_IMAGE_ICON
                    ) {
                        sound.performSoundEffect(context, KEY_CLICK, thereIsSoundEffect.value)
                        navBottomSheet.hide()
                        imageLaunch.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                }
                item {
                    CommonOptionItem(
                        name = "Add Audio",
                        icon = CASSETTE_ICON
                    ) {
                        scope.launch {
                            sound.performSoundEffect(context, KEY_CLICK, thereIsSoundEffect.value)
                            navBottomSheet.hide()
                            delay(200)
                        }.invokeOnCompletion {
                            if (!readMediaPermissions.allPermissionsGranted) {
                                if (readMediaPermissions.shouldShowRationale) {
                                    navBottomSheet.show(
                                        RationalScreen(
                                            permissionState = reminderPermissions,
                                            permissionName = "local media"
                                        )
                                    )
                                } else {
                                    readMediaPermissions.launchMultiplePermissionRequest()
                                }
                            } else {
                                navBottomSheet.show(AudioListScreen(id))
                            }
                        }
                    }
                }
//                item {
//                    OptionItem(
//                        name = "Take Photo",
//                        icon = CAMERA_ICON
//                    ) {
//                        Toast.makeText(context, "Coming Soon.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                item {
//                    OptionItem(
//                        name = "Record",
//                        icon = MIC_ICON
//                    ) {
//                        sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
//                        navBottomSheet.hide()
//                        if (!recorderPermissions.allPermissionsGranted) {
//                            if (recorderPermissions.shouldShowRationale) {
//                                recorderRationalDialog.value = true
//                            } else {
//                                recorderPermissions.launchMultiplePermissionRequest()
//                RationalScreen(
//                    permissionState = readMediaPermissions,
//                    permissionName = "audio record"
//                )
//                            }
//                        } else {
//                            navBottomSheet.show(RecorderScreen(uid) {})
//                        }
//                    }
//                }

                item {
                    CommonOptionItem(
                        name = "Tags",
                        icon = TAGS_ICON
                    ) {
                        sound.performSoundEffect(context, KEY_CLICK, thereIsSoundEffect.value)
                        navigator.push(TagsScreen(id = id))
                    }
                }
                item {
                    CommonOptionItem(
                        name = "Task List",
                        icon = LIST_CHECK_ICON
                    ) {
                        sound.performSoundEffect(context, KEY_CLICK, thereIsSoundEffect.value)
                        navigator.push(TasksScreen(id = id))
                    }
                }
                item {
                    CommonOptionItem(
                        name = "Reminder",
                        icon = BELL_ICON
                    ) {
                        sound.performSoundEffect(context, KEY_CLICK, thereIsSoundEffect.value)
                        scope.launch {
                            navBottomSheet.hide()
                            delay(200)
                        }.invokeOnCompletion {
                            if (!reminderPermissions.allPermissionsGranted) {
                                if (reminderPermissions.shouldShowRationale) {
                                    navBottomSheet.show(
                                        RationalScreen(
                                            permissionState = readMediaPermissions,
                                            permissionName = "alarm manager"
                                        )
                                    )
                                } else {
                                    reminderPermissions.launchMultiplePermissionRequest()
                                }
                            } else {
                                navBottomSheet.show(
                                    ReminderScreen(
                                        uid = id,
                                        title = titleState?.text.toString(),
                                        message = descriptionState?.text.toString()
                                    )
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.width(16.dp))

                    PriorityRow {
                        workspaceModel.updatePriority(it)
                        priorityState.value = it
                    }
                }
            }
        })
    }

    @Composable
    private fun PriorityRow(onClick: (String) -> Unit) {
        val priorities = arrayOf(NON, LOW, MED, HIG, URG)

        LazyRow(modifier = Modifier.animateContentSize()) {
            items(priorities) { priority ->
                if (priorityState.value == priority.priority) {
                    AnimatedVisibility(true) {
                        FilledTonalButton(
                            modifier = Modifier.height(34.dp),
                            enabled = true,
                            onClick = {
                                onClick.invoke(priority.priority)
                            },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = priority.color,
                            ),
                        ) {
                            Text(
                                text = priority.priority,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                } else {
                    FilledTonalIconButton(
                        modifier = Modifier.height(34.dp),
                        enabled = true,
                        onClick = {
                            onClick.invoke(priority.priority)
                        },
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = priority.color,
                        ),
                    ) {
                    }
                }
            }
        }
    }
}