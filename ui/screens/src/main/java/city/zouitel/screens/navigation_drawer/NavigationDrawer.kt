package city.zouitel.screens.navigation_drawer

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.logic.sharApp
import city.zouitel.screens.about_screen.AboutScreen
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.main_screen.MainScreen
import city.zouitel.screens.settings_screen.SettingsScreen
import city.zouitel.screens.sound
import city.zouitel.screens.tags_screen.HashTagsScreen
import city.zouitel.systemDesign.CommonConstants.APP_NAME
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.CommonIcons.CIRCLE_ICON_18
import city.zouitel.systemDesign.CommonIcons.COMMENT_EXCLAMATION
import city.zouitel.systemDesign.CommonIcons.HOME_ICON
import city.zouitel.systemDesign.CommonIcons.INTERROGATION_ICON
import city.zouitel.systemDesign.CommonIcons.SETTINGS_ICON
import city.zouitel.systemDesign.CommonIcons.SHARE_ICON
import city.zouitel.systemDesign.CommonIcons.TAGS_ICON
import city.zouitel.systemDesign.CommonIcons.REMOVE_ICON
import city.zouitel.tags.ui.TagScreenModel
import com.karacca.beetle.Beetle
import kotlinx.coroutines.launch
import kotlin.text.Typography

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NavigationDrawer(
    tagModel: TagScreenModel,
    dataStoreModel: DataStoreScreenModel,
    drawerState: DrawerState,
    homeScreen: MainScreenModel
    ) {
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow
    val observeLabels by remember(tagModel, tagModel::getAllLTags).collectAsState()
    val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::getSound).collectAsState()
    val scope = rememberCoroutineScope()
    var maxLines by remember { mutableIntStateOf(3) }

    DismissibleDrawerSheet(
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
                    modifier = Modifier.padding(10.dp)
                )
                HorizontalDivider()
            }
            item {
                NavigationDrawerItem(
                    label = { Text("Notes") },
                    icon = { Icon(painterResource(HOME_ICON), null) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                            navigator.push(MainScreen(true))
                            drawerState.close()
                        }
                    }
                )
            }
            item {
                HorizontalDivider()
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Tags") },
                    icon = { Icon(painterResource(TAGS_ICON), null) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            sound.makeSound(context, KEY_CLICK,thereIsSoundEffect.value)
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
                    )
                ) { index ->
//                    observeLabels.value.forEach { tag ->
                        ElevatedFilterChip(
                            modifier = Modifier.padding(1.5.dp),
                            selected = true,
                            onClick = {
                                scope.launch {
                                    sound.makeSound(context, KEY_CLICK,thereIsSoundEffect.value)
                                    homeScreen.updateSearchTag(observeLabels[index])
                                    homeScreen.updateSearchTitle(observeLabels[index].label ?: "")
                                    drawerState.close()
                                }
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = CIRCLE_ICON_18),
                                    contentDescription = null,
                                    tint = Color(observeLabels[index].color),
                                    modifier = Modifier.size(10.dp)
                                )
                            },
                            label = {
                                observeLabels[index].label?.let { Text(it, fontSize = 11.sp) }
                            },
                            shape = CircleShape
                        )
                    }
//                }
            }

            item {
                HorizontalDivider()
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    icon = { Icon(painterResource(SETTINGS_ICON), null) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            sound.makeSound(context, KEY_CLICK,thereIsSoundEffect.value)
                            navigator.push(SettingsScreen())
                            drawerState.close()
                        }
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Removed") },
                    icon = { Icon(painterResource(REMOVE_ICON), null) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            sound.makeSound(context, KEY_CLICK,thereIsSoundEffect.value)
                            navigator.push(MainScreen(false))
                            drawerState.close()
                        }
                    }
                )
            }

            item {
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Share This App") },
                    icon = { Icon(painterResource(SHARE_ICON), null) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            sharApp(context,"[COMING SOON.]")
                            sound.makeSound(context, KEY_CLICK,thereIsSoundEffect.value)
                            drawerState.close()
                        }
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Feedback & Help") },
                    icon = { Icon(painterResource(COMMENT_EXCLAMATION), null) },
                    selected = false,
                    onClick = {
                        sound.makeSound.invoke(context, KEY_CLICK,thereIsSoundEffect.value)
                        scope.launch {
                            Beetle.startFeedback()
                            drawerState.close()
                        }
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text("About") },
                    icon = { Icon(painterResource(INTERROGATION_ICON), null) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                            navigator.push(AboutScreen())
                            drawerState.close()
                        }
                    }
                )
            }
        }
    }
}