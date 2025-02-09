package city.zouitel.screens.navigation_drawer

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ContextualFlowRowOverflow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.domain.provider.SharedScreen
import city.zouitel.logic.sharApp
import city.zouitel.screens.about_screen.AboutScreen
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.navigation_drawer.NoteScreens.ABOUT_SCREEN
import city.zouitel.screens.navigation_drawer.NoteScreens.ARCHIVE_SCREEN
import city.zouitel.screens.navigation_drawer.NoteScreens.ASSISTANT_SCREEN
import city.zouitel.screens.navigation_drawer.NoteScreens.MAIN_SCREEN
import city.zouitel.screens.navigation_drawer.NoteScreens.SETTINGS_SCREEN
import city.zouitel.screens.navigation_drawer.NoteScreens.TAGS_SCREEN
import city.zouitel.screens.settings_screen.SettingsScreen
import city.zouitel.screens.tags_screen.HashTagsScreen
import city.zouitel.screens.utils.sound
import city.zouitel.systemDesign.CommonConstants.APP_NAME
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonConstants.TITLE_SIZE
import city.zouitel.systemDesign.CommonIcons.CIRCLE_ICON_18
import city.zouitel.systemDesign.CommonIcons.COMMENT_EXCLAMATION
import city.zouitel.systemDesign.CommonIcons.CROSS_SMALL_ICON
import city.zouitel.systemDesign.CommonIcons.HOME_BLANK_ICON
import city.zouitel.systemDesign.CommonIcons.HOME_ICON
import city.zouitel.systemDesign.CommonIcons.INBOX_ICON
import city.zouitel.systemDesign.CommonIcons.INTERROGATION_ICON
import city.zouitel.systemDesign.CommonIcons.SETTINGS_ICON
import city.zouitel.systemDesign.CommonIcons.SHARE_ICON
import city.zouitel.systemDesign.CommonIcons.SPARKLES_ICON
import city.zouitel.systemDesign.CommonIcons.TAGS_ICON
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.tags.ui.TagScreenModel
import com.karacca.beetle.Beetle
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NavigationDrawer(
    tagModel: TagScreenModel,
    dataStoreModel: DataStoreScreenModel,
    drawerState: DrawerState,
    mainModel: MainScreenModel
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow
    val observeAllTags by remember(tagModel, tagModel::observeAll).collectAsState()
    val isMute by remember(dataStoreModel, dataStoreModel::isMute).collectAsState()
    val homeScreen by remember(mainModel, mainModel::observeDefaults).collectAsState()
    val archiveScreen by remember(mainModel, mainModel::observeArchives).collectAsState()
    val scope = rememberCoroutineScope()
    var maxLines by remember { mutableIntStateOf(3) }
    val currentScreen = remember { mutableStateOf(MAIN_SCREEN) }

    val assistantScreen = rememberScreen(SharedScreen.Assistant)
    val mainScreen = rememberScreen(SharedScreen.MainScreen(true))
    val removedScreen = rememberScreen(SharedScreen.MainScreen(false))

    DismissibleDrawerSheet(
        drawerState = drawerState,
        modifier = Modifier
            .padding(end = 100.dp)
            .systemBarsPadding(),
        drawerShape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)
    ) {
        LazyColumn {
            item {
                Text(
                    text = APP_NAME,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(15.dp)
                )
            }

            item { HorizontalDivider() }

            item {
                NavigationDrawerItem(
                    label = { Text(text = MAIN_SCREEN.screen, fontSize = TITLE_SIZE.sp) },
                    icon = { Icon(painterResource(if(homeScreen.isEmpty()) HOME_BLANK_ICON else HOME_ICON), null) },
                    selected = currentScreen.value == MAIN_SCREEN,
                    onClick = {
                        scope.launch {
                            currentScreen.value = MAIN_SCREEN
                            navigator.push(mainScreen)
                            drawerState.close()
                        }
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text(text = ASSISTANT_SCREEN.screen, fontSize = TITLE_SIZE.sp) },
                    icon = { Icon(painterResource(SPARKLES_ICON), null) },
                    selected = currentScreen.value == ASSISTANT_SCREEN,
                    onClick = {
                        scope.launch {
                            currentScreen.value = ASSISTANT_SCREEN
                            navigator.push(assistantScreen)
                            drawerState.close()
                        }
                    }
                )
            }

            item { HorizontalDivider() }

            item {
                NavigationDrawerItem(
                    label = { Text(text = TAGS_SCREEN.screen, fontSize = TITLE_SIZE.sp) },
                    icon = { Icon(painterResource(TAGS_ICON), null) },
                    selected = currentScreen.value == TAGS_SCREEN,
                    onClick = {
                        scope.launch {
                            currentScreen.value = TAGS_SCREEN
                            navigator.push(HashTagsScreen())
                            drawerState.close()
                        }
                    }
                )
            }

            item {
                ContextualFlowRow(
                    modifier = Modifier.animateContentSize(),
                    itemCount = observeAllTags.size,
                    maxLines = maxLines,
                    overflow = ContextualFlowRowOverflow.expandOrCollapseIndicator(
                        expandIndicator = {
                            ElevatedFilterChip(
                                selected = true,
                                shape = CircleShape,
                                onClick = { maxLines += 1 },
                                label = {
                                    Text(
                                        text = "+ more",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        },
                        collapseIndicator = {
                            if (maxLines > 3) {
                                ElevatedFilterChip(
                                    selected = true,
                                    shape = CircleShape,
                                    onClick = { maxLines = 3 },
                                    label = {
                                        Icon(
                                            modifier = Modifier.size(15.dp),
                                            painter = painterResource(CROSS_SMALL_ICON),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onErrorContainer
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.errorContainer
                                    )
                                )
                            }
                        }
                    )
                ) { index ->
                    ElevatedFilterChip(
                        selected = true,
                        onClick = {
                            scope.launch {
                                sound.performSoundEffect(context, KEY_CLICK, isMute)
                                mainModel.updateSearchTag(observeAllTags.getOrNull(index))
                                mainModel.updateSearchTitle(observeAllTags.getOrNull(index)?.label ?: "")
                                drawerState.close()
                            }
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = CIRCLE_ICON_18),
                                contentDescription = null,
                                modifier = Modifier.size(10.dp)
                            )
                        },
                        label = {
                            observeAllTags.getOrNull(index)?.label.let { Text(it ?: "", fontSize = 11.sp) }
                        },
                        shape = CircleShape,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedLeadingIconColor = Color(observeAllTags.getOrNull(index)?.color ?: 0)
                        )
                    )
                    Spacer(Modifier.width(2.dp))
                }
            }

            item { HorizontalDivider() }

            item {
                NavigationDrawerItem(
                    label = { Text(text = SETTINGS_SCREEN.screen, fontSize = TITLE_SIZE.sp) },
                    icon = { Icon(painterResource(SETTINGS_ICON), null) },
                    selected = currentScreen.value == SETTINGS_SCREEN,
                    onClick = {
                        scope.launch {
                            currentScreen.value = SETTINGS_SCREEN
                            navigator.push(SettingsScreen())
                            drawerState.close()
                        }
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text(text = ARCHIVE_SCREEN.screen, fontSize = TITLE_SIZE.sp) },
                    icon = { Icon(painterResource(INBOX_ICON), null) },
                    selected = currentScreen.value == ARCHIVE_SCREEN,
                    onClick = {
                        scope.launch {
                            currentScreen.value = ARCHIVE_SCREEN
                            navigator.push(removedScreen)
                            drawerState.close()
                        }
                    }
                )
            }

            item { HorizontalDivider() }

            item {
                NavigationDrawerItem(
                    label = { Text("Share This App", fontSize = TITLE_SIZE.sp) },
                    icon = { Icon(painterResource(SHARE_ICON), null) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            sound.performSoundEffect(context, KEY_CLICK, isMute)
                            sharApp(context, "[COMING SOON.]")
                        }
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Feedback & Help", fontSize = TITLE_SIZE.sp) },
                    icon = { Icon(painterResource(COMMENT_EXCLAMATION), null) },
                    selected = false,
                    onClick = {
                        sound.performSoundEffect(context, KEY_CLICK, isMute)
                        scope.launch {
                            Beetle.startFeedback()
                            drawerState.close()
                        }
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text(text = ABOUT_SCREEN.screen, fontSize = TITLE_SIZE.sp) },
                    icon = { Icon(painterResource(INTERROGATION_ICON), null) },
                    selected = currentScreen.value == ABOUT_SCREEN,
                    onClick = {
                        scope.launch {
                            currentScreen.value = ABOUT_SCREEN
                            navigator.push(AboutScreen())
                            drawerState.close()
                        }
                    }
                )
            }
        }
    }
}