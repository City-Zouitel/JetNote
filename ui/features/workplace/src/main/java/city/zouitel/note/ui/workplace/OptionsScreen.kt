package city.zouitel.note.ui.workplace

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.domain.provider.SharedScreen
import city.zouitel.domain.utils.Action
import city.zouitel.media.model.Media
import city.zouitel.media.ui.MediaScreenModel
import city.zouitel.note.ui.utils.PriorityColorsList.HIG
import city.zouitel.note.ui.utils.PriorityColorsList.LOW
import city.zouitel.note.ui.utils.PriorityColorsList.MED
import city.zouitel.note.ui.utils.PriorityColorsList.NON
import city.zouitel.note.ui.utils.PriorityColorsList.URG
import city.zouitel.permissions.PermissionScreenModel
import city.zouitel.recoder.ui.RecorderScreen
import city.zouitel.systemDesign.CommonBottomSheet
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonIcons.BELL_ICON
import city.zouitel.systemDesign.CommonIcons.CAMERA_ICON
import city.zouitel.systemDesign.CommonIcons.GALLERY_ICON
import city.zouitel.systemDesign.CommonIcons.LIST_CHECK_ICON
import city.zouitel.systemDesign.CommonIcons.MIC_ICON
import city.zouitel.systemDesign.CommonIcons.TAGS_ICON
import city.zouitel.systemDesign.CommonIcons.VIDEO_CAMERA_ICON
import city.zouitel.systemDesign.CommonIcons.WAVEFORM_ICON
import city.zouitel.systemDesign.CommonOptionItem
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.SoundEffect
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.camera.CAMERA
import dev.icerock.moko.permissions.microphone.RECORD_AUDIO
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import dev.icerock.moko.permissions.storage.STORAGE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

data class OptionsScreen(
    val uid: String,
    val titleState: TextFieldState?,
    val descriptionState: TextFieldState?,
    val priorityState: MutableState<Int>
): Screen {
    @Composable
    override fun Content() {
        Options(
            dataStoreModel = getScreenModel(),
            workspaceModel = getScreenModel(),
            mediaModel = getScreenModel(),
            permissionsModel = getScreenModel()
        )
    }

    @SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun Options(
        dataStoreModel: DataStoreScreenModel,
        workspaceModel: WorkplaceScreenModel,
        mediaModel: MediaScreenModel,
        permissionsModel: PermissionScreenModel
    ) {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val navBottomSheet = LocalBottomSheetNavigator.current

        val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::isMute).collectAsState()
        val scope = rememberCoroutineScope()
        val sound by lazy { SoundEffect() }

        val tagsScreen = rememberScreen(SharedScreen.Tags(uid))
        val tasksScreen = rememberScreen(SharedScreen.Tasks(uid))
        val reminderScreen = rememberScreen(
            SharedScreen.Reminder(
                uid,
                titleState?.text.toString(),
                descriptionState?.text.toString()
            )
        )
        val audioListScreen = rememberScreen(SharedScreen.AudioList(uid))
        val pictureUri = remember { mutableStateOf<Uri?>(null) }
        val videoUri = remember { mutableStateOf<Uri?>(null) }

        val galleryLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia()
        ) { uris ->
            uris.forEach { uri ->
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                val mimeType = context.contentResolver.getType(uri)
                when {
                    mimeType?.startsWith("image/") == true -> {
                        Random.nextLong().let {
                            mediaModel.sendAction(
                                Action.Insert(
                                    Media(
                                        it,
                                        uid,
                                        false,
                                        uri.toString()
                                    )
                                )
                            )
                        }
                    }

                    mimeType?.startsWith("video/") == true -> {
                        Random.nextLong().let {
                            mediaModel.sendAction(
                                Action.Insert(
                                    Media(
                                        it,
                                        uid,
                                        true,
                                        uri.toString()
                                    )
                                )
                            )
                        }
                    }
                    else -> throw Exception("")
                }
            }
            navBottomSheet.hide()
        }

        val cameraLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) {
            if (it) {
                Random.nextLong().let { id ->
                    mediaModel.sendAction(
                        Action.Insert(
                            Media(
                                id,
                                uid,
                                uri = pictureUri.value.toString()
                            )
                        )
                    )
                }
            } else navBottomSheet.hide()
            navBottomSheet.hide()
        }

        val videoCapture = rememberLauncherForActivityResult(
            ActivityResultContracts.CaptureVideo()
        ) {
            if (it) {
                Random.nextLong().let { id ->
                    mediaModel.sendAction(
                        Action.Insert(
                            Media(
                                id,
                                uid,
                                uri = videoUri.value.toString(),
                                isVideo = true
                            )
                        )
                    )
                }
            } else navBottomSheet.hide()
            navBottomSheet.hide()
        }

        val getPictureUri: () -> Uri = remember {
            {
                val uri = createMediaUri(context, "jpg")
                pictureUri.value = uri
                uri
            }
        }
        val getVideoUri: () -> Uri = remember {
            {
                val uri = createMediaUri(context, "mp4")
                videoUri.value = uri
                uri
            }
        }

        Navigator(CommonBottomSheet {
            LazyColumn {
                item {
                    CommonOptionItem(
                        name = "Gallery",
                        icon = GALLERY_ICON
                    ) {
                        scope.launch {
                            sound.performSoundEffect(context, KEY_CLICK, thereIsSoundEffect.value)
                            galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                        }
                    }
                }

                item {
                    CommonOptionItem(
                        name = "Add Audio",
                        icon = WAVEFORM_ICON
                    ) {
                         scope.launch {
                            sound.performSoundEffect(context, KEY_CLICK, thereIsSoundEffect.value)
                            permissionsModel.providePermission(Permission.STORAGE).collect {
                                if(it) navigator.push(audioListScreen)
                            }
                         }
                    }
                }

                item {
                    CommonOptionItem(
                        name = "Take Photo",
                        icon = CAMERA_ICON
                    ) {
                        scope.launch {
                            sound.performSoundEffect(context, KEY_CLICK, thereIsSoundEffect.value)
                            permissionsModel.providePermission(Permission.CAMERA).collect {
                                if (it) cameraLauncher.launch(getPictureUri())
                            }
                        }
                    }
                }

                item {
                    CommonOptionItem(
                        name = "Video Capture",
                        icon = VIDEO_CAMERA_ICON
                    ) {
                        scope.launch {
                            sound.performSoundEffect(context, KEY_CLICK, thereIsSoundEffect.value)
                            permissionsModel.providePermission(Permission.CAMERA).collect {
                                if (it) videoCapture.launch(getVideoUri())
                            }
                        }
                    }
                }

                item {
                    CommonOptionItem(
                        name = "Record",
                        icon = MIC_ICON
                    ) {
                        scope.launch {
                            navBottomSheet.hide()
                            delay(250)
                            permissionsModel.providePermission(Permission.RECORD_AUDIO).collectLatest {
                                if (it) navBottomSheet.show(RecorderScreen(uid))
                            }
                        }
                    }
                }

                item {
                    CommonOptionItem(
                        name = "Tags",
                        icon = TAGS_ICON
                    ) {
                        sound.performSoundEffect(context, KEY_CLICK, thereIsSoundEffect.value)
                        navigator.push(tagsScreen)
                    }
                }

                item {
                    CommonOptionItem(
                        name = "Task List",
                        icon = LIST_CHECK_ICON
                    ) {
                        sound.performSoundEffect(context, KEY_CLICK, thereIsSoundEffect.value)
                        navigator.push(tasksScreen)
                    }
                }

                item {
                    CommonOptionItem(
                        name = "Reminder",
                        icon = BELL_ICON
                    ) {
                        scope.launch {
                            navBottomSheet.hide()
                            delay(250)
                            permissionsModel.providePermission(Permission.REMOTE_NOTIFICATION).collect {
                                if (it) navBottomSheet.show(reminderScreen)
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
    private fun PriorityRow(onClick: (Int) -> Unit) {
        val priorities = arrayOf(NON, LOW, MED, HIG, URG)

        LazyRow(modifier = Modifier.animateContentSize().padding(10.dp)) {
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
                                text = priority.name,
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

    @SuppressLint("SimpleDateFormat")
    private fun createMediaUri(context: Context, pattern: String): Uri {
        val fileName = SimpleDateFormat("yyyyMMddHHmmss").format(Date()) + ".$pattern"
        val imagePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Medias")
        if (!imagePath.exists()) imagePath.mkdirs()
        val newFile = File(imagePath, fileName)
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", newFile)
    }
}