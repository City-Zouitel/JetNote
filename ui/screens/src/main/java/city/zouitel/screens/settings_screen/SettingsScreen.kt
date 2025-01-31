package city.zouitel.screens.settings_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.assistant.ui.ApiKeyScreen
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.navigation_drawer.NavigationDrawer
import city.zouitel.screens.utils.sound
import city.zouitel.systemDesign.CommonConstants.DARK
import city.zouitel.systemDesign.CommonConstants.DEFAULT_ORDER
import city.zouitel.systemDesign.CommonConstants.GRID
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonConstants.LIGHT
import city.zouitel.systemDesign.CommonConstants.LIST
import city.zouitel.systemDesign.CommonConstants.NAME_ORDER
import city.zouitel.systemDesign.CommonConstants.NEWEST_ORDER
import city.zouitel.systemDesign.CommonConstants.OLDEST_ORDER
import city.zouitel.systemDesign.CommonConstants.PRIORITY_ORDER
import city.zouitel.systemDesign.CommonConstants.REMINDING_ORDER
import city.zouitel.systemDesign.CommonTopAppBar
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.tags.ui.TagScreenModel
import com.skydoves.cloudy.cloudy

class SettingsScreen: Screen {

    @Composable
    override fun Content() {

        Settings(
            tagModel = getScreenModel(),
            datastoreModel = getScreenModel(),
            mainScreen = getScreenModel()
        )
    }

    @SuppressLint(
        "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Settings(
        tagModel: TagScreenModel,
        mainScreen: MainScreenModel,
        datastoreModel: DataStoreScreenModel
    ) {

        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val navBottom = LocalBottomSheetNavigator.current

        val themeState by remember(datastoreModel, datastoreModel::getTheme).collectAsState()
        val isMute by remember(datastoreModel, datastoreModel::isMute).collectAsState()
        val layoutState = remember(datastoreModel, datastoreModel::getLayout).collectAsState()
        val orderState = remember(datastoreModel, datastoreModel::getOrdination).collectAsState()
        val screenshotState by remember(datastoreModel, datastoreModel::getScreenshotBlock).collectAsState()
        val appLockState by remember(datastoreModel, datastoreModel::isLockMode).collectAsState()
        val apiKeyState by remember(datastoreModel, datastoreModel::getGeminiApiKey).collectAsState()

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
                .cloudy(enabled = navBottom.isVisible)
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
                            description = "This mode is a system-wide setting that shifts the color scheme of your Android app's UI to darker colors.\nIt's designed to Reduce eye strain, improve accessibility, save battery life, and enhance user experience.",
                            checked = themeState == DARK,
                            onChecked = {
                                sound.performSoundEffect(context, KEY_CLICK, isMute)
                                datastoreModel.setTheme(if (themeState == DARK) LIGHT else DARK)
                            }
                        )
                    }

                    item { HorizontalDivider(modifier = Modifier.padding(10.dp)) }

                    item {
                        PreferenceItem(
                            title = "Is Mute",
                            description = "App muting refers to the ability to silence or suppress audio output from this application.\nThis feature is typically used to prevent unwanted notifications and sounds, or interrupting the user.",
                            checked = isMute,
                            onChecked = {
                                sound.performSoundEffect(context, KEY_CLICK, isMute)
                                datastoreModel.setMute(!isMute)
                            }
                        )
                    }

                    item { HorizontalDivider(modifier = Modifier.padding(10.dp)) }

                    item {
                        PreferenceItem(
                            title = "Layout",
                            description = "Layouts are fundamental to organizing and displaying content in your Android applications.\nThey define the structure and arrangement of UI elements on the screen.\nChoosing the right layout is crucial for creating a user-friendly and visually appealing interface.",
                            currentItem = layoutState.value,
                            items = listOf(GRID, LIST)
                        ) { layout ->
                            sound.performSoundEffect(context, KEY_CLICK, isMute)
                            datastoreModel.setLayout(layout)
                        }
                    }

                    item { HorizontalDivider(modifier = Modifier.padding(10.dp)) }

                    item {
                        PreferenceItem(
                            title = "Sort Notes By",
                            description = null,
                            items = listOf(
                                NAME_ORDER,
                                DEFAULT_ORDER,
                                OLDEST_ORDER,
                                NEWEST_ORDER,
                                PRIORITY_ORDER,
                                REMINDING_ORDER
                            ),
                            currentItem = orderState.value
                        ) { order ->
                            sound.performSoundEffect(context, KEY_CLICK, isMute)
                            datastoreModel.setOrdination(order)
                        }
                    }

                    item { HorizontalDivider(modifier = Modifier.padding(10.dp)) }

                    item {
                        PreferenceItem(
                            title = "Block Screenshots",
                            description = "Screenshot blocking is a security measure that prevents users from capturing screenshots of your app's content.\nThis is often used to protect sensitive information, such as banking details, personal data, or copyrighted material.",
                            checked = screenshotState,
                            onChecked = {
                                sound.performSoundEffect(context, KEY_CLICK, isMute)
                                datastoreModel.setScreenshotBlock(it)
                            }
                        )
                    }

                    item { HorizontalDivider(modifier = Modifier.padding(10.dp)) }

                    item {
                        PreferenceItem(
                            title = "Lock Mode",
                            description = "This feature enhances the security of your app by requiring" +
                                    " users to authenticate before they can access its content." +
                                    " This is typically achieved using the device's built-in security features," +
                                    " such as fingerprint authentication, PIN, or pattern unlock.",
                            checked = appLockState,
                            onChecked = {
                                sound.performSoundEffect(context, KEY_CLICK, isMute)
                                datastoreModel.setLockMode(it)
                            }
                        )
                    }

                    item { HorizontalDivider(modifier = Modifier.padding(10.dp)) }

                    item {
                        PreferenceItem(
                            title = "AI Generative",
                            description = "Generative AI is a type of artificial intelligence that can create new content and ideas." +
                                    " It learns patterns and structures from existing data and then uses this knowledge to generate new," +
                                    " original outputs.",
                            checked = null
                        ) {
                            navBottom.show(ApiKeyScreen(apiKeyState))
                        }
                    }

                    item { HorizontalDivider(modifier = Modifier.padding(10.dp)) }

                    item {
                        PreferenceItem(
                            title = "Licenses",
                            description = "Public repositories on GitHub are often used to share open source software.",
                            checked = null,
                        ) {
                            sound.performSoundEffect(context, KEY_CLICK, isMute)
                            navigator.push(Licenses())
                        }
                    }
                }
            }
        }
    }
}