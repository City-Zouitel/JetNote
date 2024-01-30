package city.zouitel.navigation.settings_screen

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import city.zouitel.navigation.getMaterialColor
import city.zouitel.navigation.navigation_drawer.NavigationDrawer
import city.zouitel.navigation.sound
import city.zouitel.systemDesign.AdaptingRowBetween
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.MaterialColors.Companion.ON_SURFACE
import city.zouitel.systemDesign.MaterialColors.Companion.SURFACE
import city.zouitel.navigation.top_action_bar.CustomTopAppBar
import city.zouitel.systemDesign.Cons.LICENSES_ROUTE
import org.koin.androidx.compose.koinViewModel

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedMaterialScaffoldPaddingParameter"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    dataStoreVM: DataStoreVM = koinViewModel(),
    navC: NavController
) {
    val ctx = LocalContext.current
    val isDarkTheme = remember(dataStoreVM, dataStoreVM::getTheme).collectAsState()
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val lazyListState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
                        NavigationDrawer(
                            navController = navC,
                            drawerState = drawerState,
                            searchTagEntity = null,
                            searchTitle = null
                        )
        },
        modifier = Modifier.navigationBarsPadding()
    ){
        androidx.compose.material.Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = getMaterialColor(SURFACE),
            topBar = {
                CustomTopAppBar(
                    drawerState = drawerState,
                    topAppBarScrollBehavior = scrollBehavior,
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
                        sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                        dataStoreVM.setTheme(
                            if (isDarkTheme.value == "DARK") "LITE" else "DARK"
                        )
                    }
                }

                item {
                    Divider(modifier = Modifier.padding(10.dp))
                }

                item {
                    PreferenceItem(
                        title = "Sound Effect",
                        description = "Make effectSound when any key is pressed.",
                        active = thereIsSoundEffect.value
                    ) {
                        sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                        dataStoreVM.setSound(!thereIsSoundEffect.value)
                    }
                }

                item {
                    Divider(modifier = Modifier.padding(10.dp))
                }

                item {
                    PreferenceItem(title = "Licenses", description = "Public repositories on GitHub are often used to share open source software.") {
                        sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                        with(navC) {
                            navigate(LICENSES_ROUTE)
                            clearBackStack(LICENSES_ROUTE)
                        }
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
                color = getMaterialColor(ON_SURFACE)
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
                color = getMaterialColor(ON_SURFACE)
            )
        }
    }
}