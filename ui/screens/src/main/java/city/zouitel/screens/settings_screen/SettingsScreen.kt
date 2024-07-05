package city.zouitel.screens.settings_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.navigation_drawer.NavigationDrawer
import city.zouitel.screens.sound
import city.zouitel.systemDesign.AdaptingRowBetween
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonTopAppBar
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.tags.ui.TagScreenModel

class SettingsScreen: Screen {

    @Composable
    override fun Content() {

        Settings(
            tagModel = getScreenModel<TagScreenModel>(),
            datastoreModel = getScreenModel<DataStoreScreenModel>(),
            mainScreen = getScreenModel<MainScreenModel>()
        )
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Settings(
        tagModel: TagScreenModel,
        datastoreModel: DataStoreScreenModel,
        mainScreen: MainScreenModel
    ) {

        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        val isDarkTheme = remember(datastoreModel, datastoreModel::getTheme).collectAsState()
        val thereIsSoundEffect = remember(datastoreModel, datastoreModel::getSound).collectAsState()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val topAppBarState = rememberTopAppBarState()
        val lazyListState = rememberLazyListState()
        val scaffoldState = rememberScaffoldState()

        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                NavigationDrawer(
                    dataStoreModel = datastoreModel,
                    tagModel = tagModel,
                    drawerState = drawerState,
                    homeScreen = mainScreen
                )
            },
            modifier = Modifier.navigationBarsPadding()
        ) {
            androidx.compose.material.Scaffold(
                scaffoldState = scaffoldState,
                backgroundColor = MaterialTheme.colorScheme.surface,
                topBar = {
                    CommonTopAppBar(
                        datastoreModel = datastoreModel,
                        drawerState = drawerState,
                        scrollBehavior = scrollBehavior,
                        title = "Settings"
                    )
                },
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

                ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState
                ) {

                    item {
                        PreferenceItem(
                            title = "Dark Mode",
                            description = "This application following the system mode by default.",
                            active = isDarkTheme.value == "DARK"
                        ) {
                            sound.makeSound.invoke(context, KEY_CLICK, thereIsSoundEffect.value)
                            datastoreModel.setTheme(
                                if (isDarkTheme.value == "DARK") "LITE" else "DARK"
                            )
                        }
                    }

                    item {
                        HorizontalDivider(modifier = Modifier.padding(10.dp))
                    }

                    item {
                        PreferenceItem(
                            title = "Sound Effect",
                            description = "Make effectSound when any key is pressed.",
                            active = thereIsSoundEffect.value
                        ) {
                            sound.makeSound.invoke(context, KEY_CLICK, thereIsSoundEffect.value)
                            datastoreModel.setSound(!thereIsSoundEffect.value)
                        }
                    }

                    item {
                        HorizontalDivider(modifier = Modifier.padding(10.dp))
                    }

                    item {
                        PreferenceItem(
                            title = "Licenses",
                            description = "Public repositories on GitHub are often used to share open source software."
                        ) {
                            sound.makeSound.invoke(context, KEY_CLICK, thereIsSoundEffect.value)
                            navigator.push(Licenses())
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun PreferenceItem(
        title: String,
        description: String? = null,
        active: Boolean = false,
        onItemClick: () -> Unit = { },
    ) {
        Column(
            modifier = Modifier
                .clickable { onItemClick() }
                .padding(start = 32.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
        ) {
            AdaptingRowBetween(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Canvas(
                    modifier = Modifier
                        .size(10.dp)
                ) {
                    drawArc(
                        color = if (active) Color.Green else Color.Transparent,
                        startAngle = 1f,
                        sweepAngle = 360f,
                        useCenter = true,
                        style = Fill
                    )
                }
            }
            if (description != null) {
                Text(
                    text = description,
                    Modifier.alpha(.5f),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}