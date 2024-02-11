package city.zouitel.navigation.navigation_drawer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import city.zouitel.navigation.sound
import city.zouitel.systemDesign.Cons.ABOUT_ROUTE
import city.zouitel.systemDesign.Cons.APP_NAME
import city.zouitel.systemDesign.Cons.HOME_ROUTE
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.Cons.SETTING_ROUTE
import city.zouitel.systemDesign.Cons.TAG_ROUTE
import city.zouitel.systemDesign.Cons.TRASH_ROUTE
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.CIRCLE_ICON_18
import city.zouitel.systemDesign.Icons.COMMENT_EXCLAMATION
import city.zouitel.systemDesign.Icons.HOME_ICON
import city.zouitel.systemDesign.Icons.INTERROGATION_ICON
import city.zouitel.systemDesign.Icons.SETTINGS_ICON
import city.zouitel.systemDesign.Icons.SHARE_ICON
import city.zouitel.systemDesign.Icons.TAGS_ICON
import city.zouitel.systemDesign.Icons.TRASH_ICON
import city.zouitel.systemDesign.sharApp
import city.zouitel.tags.viewmodel.TagViewModel
import city.zouitel.tags.model.Tag
import com.karacca.beetle.Beetle
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    tagViewModel: TagViewModel = koinViewModel(),
    dataStoreVM: DataStoreVM = koinViewModel(),
    drawerState: DrawerState,
    navController: NavController,
    searchTitle: MutableState<String>?,
    searchTagEntity: MutableState<Tag>?
) {
    val context = LocalContext.current
    val observeLabels = remember(tagViewModel,tagViewModel::getAllLTags).collectAsState()
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()
    val scope = rememberCoroutineScope()

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
                Divider()
            }
            item {
                NavigationDrawerItem(
                    label = { Text("Notes") },
                    icon = { Icon(painterResource(HOME_ICON), null) },
                    selected = false,
                    onClick = {
                        sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                        with(navController) {
                            navigate(HOME_ROUTE)
                            clearBackStack(HOME_ROUTE)
                        }
                    }
                )
            }
            item {
                Divider()
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Tags") },
                    icon = { Icon(painterResource(TAGS_ICON), null) },
                    selected = false,
                    onClick = {
                        sound.makeSound(context, KEY_CLICK,thereIsSoundEffect.value)
                        with(navController) {
                            navigate("$TAG_ROUTE/${null}")
                            clearBackStack("$TAG_ROUTE/${null}")
                        }
                    }
                )
            }

            item {
                com.google.accompanist.flowlayout.FlowRow(
                    mainAxisSpacing = 3.dp
                ) {
                    observeLabels.value.forEach { label ->
                        ElevatedFilterChip(
                            selected = true,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    sound.makeSound(context, KEY_CLICK,thereIsSoundEffect.value)
                                }
                                searchTitle?.value = label.label!!
                                searchTagEntity?.value = label
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = CIRCLE_ICON_18),
                                    contentDescription = null,
                                    tint = Color(label.color),
                                    modifier = Modifier.size(10.dp)
                                )
                            },
                            label = {
                                label.label?.let { Text(it, fontSize = 11.sp) }
                            },
                            shape = CircleShape
                        )
                    }
                }
            }

            item {
                Divider()
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    icon = { Icon(painterResource(SETTINGS_ICON), null) },
                    selected = false,
                    onClick = {
                        sound.makeSound(context, KEY_CLICK,thereIsSoundEffect.value)
                        with(navController) {
                            navigate(SETTING_ROUTE)
                            clearBackStack(SETTING_ROUTE)
                        }
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Removed") },
                    icon = { Icon(painterResource(TRASH_ICON), null) },
                    selected = false,
                    onClick = {
                        sound.makeSound(context, KEY_CLICK,thereIsSoundEffect.value)
                        with(navController) {
                            navigate(TRASH_ROUTE)
                            clearBackStack(TRASH_ROUTE)
                        }
                    }
                )
            }

            item {
                Divider()
                NavigationDrawerItem(
                    label = { Text("Share This App") },
                    icon = { Icon(painterResource(SHARE_ICON), null) },
                    selected = false,
                    onClick = {
                        sharApp(context,"[COMING SOON.]")
                        sound.makeSound(context, KEY_CLICK,thereIsSoundEffect.value)
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
                            drawerState.close()
                        }
//                        mailTo(ctx,"mailto:example@gmail.com")
                        Beetle.startFeedback()
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text("About") },
                    icon = { Icon(painterResource(INTERROGATION_ICON), null) },
                    selected = false,
                    onClick = {
                        sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                        with(navController) {
                            navigate(ABOUT_ROUTE)
                            clearBackStack(ABOUT_ROUTE)
                        }
                    }
                )
            }
        }
    }
}