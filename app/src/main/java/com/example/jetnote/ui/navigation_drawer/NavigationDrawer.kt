package com.example.jetnote.ui.navigation_drawer

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetnote.cons.*
import com.example.jetnote.db.entities.label.Label
import com.example.jetnote.fp.mailTo
import com.example.jetnote.fp.sharApp
import com.example.jetnote.icons.*
import com.example.jetnote.vm.LabelVM
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    labelVM: LabelVM = hiltViewModel(),
    drawerState: DrawerState,
    navController: NavController,
    searchTitle: MutableState<String>?,
    searchLabel: MutableState<Label>?
) {
    val ctx = LocalContext.current
    val observeLabels = remember(labelVM,labelVM::getAllLabels).collectAsState()

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
                    onClick = { navController.navigate(HOME_ROUTE) }
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
                        text = "Labels",
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
                                navController.navigate("labels/${null}")
                            }
                    )
                }
            }

            item {
                FlowRow(
                    mainAxisSpacing = 3.dp
                ) {
                    observeLabels.value.forEach { label ->
                        ElevatedFilterChip(
                            selected = true,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                                searchTitle?.value = label.label!!
                                searchLabel?.value = label
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

            }
            item {
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    icon = { Icon(painterResource(SETTINGS_ICON), null) },
                    selected = false,
                    onClick = {
                        navController.navigate(SETTING_ROUTE)
                    }
                )
            }

            item {

                NavigationDrawerItem(
                    label = { Text("Trash") },
                    icon = { Icon(painterResource(TRASH_ICON), null) },
                    selected = false,
                    onClick = { navController.navigate(TRASH_ROUTE) }
                )
            }
            item {
                Divider()
                NavigationDrawerItem(
                    label = { Text("Share This App") },
                    icon = { Icon(painterResource(SHARE_ICON), null) },
                    selected = false,
                    onClick = { sharApp(ctx,"[YOUR APP LINK]") }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Feedback & Help") },
                    icon = { Icon(painterResource(COMMENT_EXCLAMATION), null) },
                    selected = false,
                    onClick = {
                      mailTo(ctx,"mailto:example@gmail.com")

//                        Beetle.startFeedback()
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text("About") },
                    icon = { Icon(painterResource(INTERROGATION_ICON), null) },
                    selected = false,
                    onClick = {

                    }
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 55.dp)
                ) {
                    Text(
                        text = "version: $APP_VERSION",
                        fontSize = 8.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}