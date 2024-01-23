package city.zouitel.navigation.navigation_drawer

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import city.zouitel.systemDesign.Cons.APP_NAME
import city.zouitel.systemDesign.Cons.HOME_ROUTE
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.Cons.SETTING_ROUTE
import city.zouitel.systemDesign.Cons.TRASH_ROUTE
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.CIRCLE_ICON_18
import city.zouitel.systemDesign.Icons.COMMENT_EXCLAMATION
import city.zouitel.systemDesign.Icons.HOME_ICON
import city.zouitel.systemDesign.Icons.INTERROGATION_ICON
import city.zouitel.systemDesign.Icons.SETTINGS_ICON
import city.zouitel.systemDesign.Icons.SHARE_ICON
import city.zouitel.systemDesign.Icons.TRASH_ICON
import city.zouitel.systemDesign.sharApp
import city.zouitel.tags.viewmodel.TagViewModel
import city.zouitel.tags.model.Tag
import city.zouitel.navigation.sound
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
    val ctx = LocalContext.current
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
                        navController.navigate(HOME_ROUTE)
                        sound.makeSound(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    }
                )
            }
            item {
                Divider()
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tags",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(15.dp)
                    )
                    Text(
                        text = "Edit",
                        style = TextStyle(
                            fontSize = 12.sp,
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier
                            .padding(15.dp)
                            .clickable {
                                navController.navigate("tagEntities/${null}")
                                sound.makeSound(ctx, KEY_CLICK, thereIsSoundEffect.value)
                            }
                    )
                }
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
                                    sound.makeSound(ctx, KEY_CLICK,thereIsSoundEffect.value)
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
                        navController.navigate(SETTING_ROUTE)
                        sound.makeSound(ctx, KEY_CLICK,thereIsSoundEffect.value)

                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Trash") },
                    icon = { Icon(painterResource(TRASH_ICON), null) },
                    selected = false,
                    onClick = {
                        navController.navigate(TRASH_ROUTE)
                        sound.makeSound(ctx, KEY_CLICK,thereIsSoundEffect.value)
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
                        sharApp(ctx,"[COMING SOON.]")
                        sound.makeSound(ctx, KEY_CLICK,thereIsSoundEffect.value)
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Feedback & Help") },
                    icon = { Icon(painterResource(COMMENT_EXCLAMATION), null) },
                    selected = false,
                    onClick = {
                        sound.makeSound.invoke(ctx, KEY_CLICK,thereIsSoundEffect.value)
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
                        navController.navigate("about")
                        sound.makeSound(ctx, KEY_CLICK,thereIsSoundEffect.value)
                    }
                )
            }
        }
    }
}