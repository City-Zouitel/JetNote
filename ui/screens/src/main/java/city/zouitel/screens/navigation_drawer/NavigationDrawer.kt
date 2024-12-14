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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.assistant.ui.AssistantScreen
import city.zouitel.logic.sharApp
import city.zouitel.screens.about_screen.AboutScreen
import city.zouitel.screens.main_screen.MainScreen
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.navigation_drawer.NoteScreens.ABOUT_SCREEN
import city.zouitel.screens.navigation_drawer.NoteScreens.ASSISTANT_SCREEN
import city.zouitel.screens.navigation_drawer.NoteScreens.MAIN_SCREEN
import city.zouitel.screens.navigation_drawer.NoteScreens.REMOVED_SCREEN
import city.zouitel.screens.navigation_drawer.NoteScreens.SETTINGS_SCREEN
import city.zouitel.screens.navigation_drawer.NoteScreens.TAGS_SCREEN
import city.zouitel.screens.settings_screen.SettingsScreen
import city.zouitel.screens.tags_screen.HashTagsScreen
import city.zouitel.screens.utils.sound
import city.zouitel.systemDesign.CommonConstants.APP_NAME
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonConstants.TITLE_SIZE
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonIcons.CIRCLE_ICON_18
import city.zouitel.systemDesign.CommonIcons.COMMENT_EXCLAMATION
import city.zouitel.systemDesign.CommonIcons.HOME_ICON
import city.zouitel.systemDesign.CommonIcons.INTERROGATION_ICON
import city.zouitel.systemDesign.CommonIcons.REMOVE_ICON
import city.zouitel.systemDesign.CommonIcons.SETTINGS_ICON
import city.zouitel.systemDesign.CommonIcons.SHARE_ICON
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
    navigationDrawerModel: NavigationDrawerScreenModel,
    drawerState: DrawerState,
    homeScreen: MainScreenModel
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow
    val observeLabels by remember(tagModel, tagModel::getAllLTags).collectAsState()
    val isMute by remember(dataStoreModel, dataStoreModel::isMute).collectAsState()
    val scope = rememberCoroutineScope()
    var maxLines by remember { mutableIntStateOf(3) }
    val currentScreen = remember { mutableStateOf(MAIN_SCREEN) }

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
                    icon = { Icon(painterResource(HOME_ICON), null) },
                    selected = currentScreen.value == MAIN_SCREEN,
                    onClick = {
                        scope.launch {
                            currentScreen.value = MAIN_SCREEN
                            navigator.push(MainScreen(isHome = true))
                            drawerState.close()
                        }
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text(text = ASSISTANT_SCREEN.screen, fontSize = TITLE_SIZE.sp) },
                    icon = { Icon(painterResource(HOME_ICON), null) },
                    selected = currentScreen.value == ASSISTANT_SCREEN,
                    onClick = {
                        scope.launch {
                            currentScreen.value = ASSISTANT_SCREEN
                            navigator.push(AssistantScreen())
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
                    itemCount = observeLabels.size,
                    maxLines = maxLines,
                    overflow = ContextualFlowRowOverflow.expandOrCollapseIndicator(
                        expandIndicator = {
                            ElevatedFilterChip(
                                selected = true,
                                shape = CircleShape,
                                onClick = { maxLines += 1 },
                                label = {
                                    Text("+${totalItemCount - shownItemCount}")
                                }
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
                                            painter = painterResource(CommonIcons.CROSS_ICON),
                                            contentDescription = null
                                        )
                                    }
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
                                homeScreen.updateSearchTag(observeLabels[index])
                                homeScreen.updateSearchTitle(observeLabels[index].label ?: "")
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
                            observeLabels[index].label?.let { Text(it, fontSize = 11.sp) }
                        },
                        shape = CircleShape,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedLeadingIconColor = Color(observeLabels[index].color)
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
                    label = { Text(text = REMOVED_SCREEN.screen, fontSize = TITLE_SIZE.sp) },
                    icon = { Icon(painterResource(REMOVE_ICON), null) },
                    selected = currentScreen.value == REMOVED_SCREEN,
                    onClick = {
                        scope.launch {
                            currentScreen.value = REMOVED_SCREEN
                            navigator.push(MainScreen(false))
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