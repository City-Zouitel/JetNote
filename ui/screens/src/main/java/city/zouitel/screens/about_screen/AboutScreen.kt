package city.zouitel.screens.about_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.screens.navigation_drawer.NavigationDrawer
import city.zouitel.screens.top_action_bar.CustomTopAppBar
import city.zouitel.tags.viewmodel.TagScreenModel

class AboutScreen: Screen {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val topAppBarState = rememberTopAppBarState()
        val lazyListState = rememberLazyListState()
        val scaffoldState = rememberScaffoldState()

        val tagModel = getScreenModel<TagScreenModel>()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                NavigationDrawer(
                    tagModel = tagModel,
                    drawerState = drawerState,
                    searchTagEntity = null,
                    searchTitle = null
                )
            },
            modifier = Modifier.navigationBarsPadding()
        ) {
            androidx.compose.material.Scaffold(
                scaffoldState = scaffoldState,
                backgroundColor = MaterialTheme.colorScheme.surface,
                topBar = {
                    CustomTopAppBar(
                        drawerState = drawerState,
                        topAppBarScrollBehavior = scrollBehavior,
                        title = "About"
                    )
                },
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

                ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState
                ) {
                    item { AboutLabel() }
                    item { AboutDescription() }
                    item { ProjectSources() }
                }
            }
        }
    }
}

