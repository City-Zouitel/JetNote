package city.zouitel.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.links.ui.NoteAndLinkScreenModel
import city.zouitel.note.model.Data
import city.zouitel.note.model.Note
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonConstants.KEY_STANDARD
import city.zouitel.systemDesign.CommonConstants.NON
import city.zouitel.systemDesign.CommonIcons.CALENDAR_ICON
import city.zouitel.systemDesign.CommonIcons.CLOCK_ICON
import city.zouitel.systemDesign.CommonIcons.ERASER_ICON
import city.zouitel.systemDesign.CommonIcons.REFRESH_ICON
import city.zouitel.systemDesign.CommonIcons.UNDO_ICON
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.SoundEffect
import java.util.Date

    @Composable
     fun OptionsDialog(
        data: Data?,
        dataStoreModel: DataStoreScreenModel,
        dataModel: DataScreenModel,
        mainModel: MainScreenModel,
        linkModel: LinkScreenModel,
        noteAndLinkModel: NoteAndLinkScreenModel,
        ) {
        val context = LocalContext.current

        val soundEffect = remember(dataStoreModel, dataStoreModel::getSound).collectAsState()
        val sound = SoundEffect()
        val observerRemovedNotes = remember(mainModel, mainModel::allTrashedNotes).collectAsState()
        val uiState by remember(mainModel, mainModel::uiState).collectAsState()

        AlertDialog(
            onDismissRequest = {
                mainModel.updateOptionsDialog(false)
            },
            title = {
                Row {
                    CommonRow(modifier = Modifier.fillMaxWidth()) {
                        Text(text = uiState.selectedNote?.title ?: "Unspecified", fontSize = 25.sp)
                    }
                }
            },
            text = {
                Column {
                    OutlinedIconButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = {
                            sound.makeSound(context, KEY_CLICK, soundEffect.value)
                            uiState.selectedNote?.copy(removed = 0)?.let {
                                dataModel.editData(
                                    it
                        //                                Data(
                        //                                    title = uiState.selectedNote?.title,
                        //                                    description = uiState.selectedNote?.description,
                        //                                    priority = uiState.selectedNote?.priority ?: NON,
                        //                                    uid = uiState.selectedNote?.uid ?: "",
                        //                                    removed = 0,
                        //                                    color = uiState.selectedNote?.color ?: 0,
                        //                                    textColor = uiState.selectedNote?.textColor ?: 0
                        //                                )
                                )
                            }
                            mainModel.updateOptionsDialog(false)
                        }) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp)
                        ) {
                            Icon(
                                painterResource(UNDO_ICON), null,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Rollback", fontSize = 17.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedIconButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = {
                            sound.makeSound(context, KEY_CLICK, soundEffect.value)

                            uiState.selectedNote?.let { dataModel.deleteData(it) }

                            observerRemovedNotes.value.forEach { entity ->
                                entity.linkEntities.forEach { link ->
                                    linkModel.deleteLink(link)
                                    noteAndLinkModel.deleteNoteAndLink(
                                        NoteAndLink(
                                            noteUid = entity.dataEntity.uid,
                                            linkId = link.id
                                        )
                                    )
                                }
                                entity.taskEntities.forEach {
                                    // TODO: need finishing.
                                }
                            }
                            mainModel.updateOptionsDialog(false)

                        }) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp)
                        ) {
                            Icon(
                                painterResource(ERASER_ICON), null,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Erase", fontSize = 17.sp)
                        }
                    }
                }
            },
            confirmButton = {  },
            dismissButton = {
                OutlinedIconButton(
                    modifier = Modifier.size(90.dp,35.dp),
                    onClick = {
                        sound.makeSound(context, KEY_CLICK, soundEffect.value)
                        mainModel.updateOptionsDialog(false)
                    }) {
                    Text(text = "Cancel", fontSize = 16.sp)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
