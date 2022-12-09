package com.example.jetnote.ui.settings_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetnote.icons.OPACITY_ICON
import com.example.jetnote.cons.SURFACE
import com.example.jetnote.ds.DataStore
import com.example.jetnote.fp.getMaterialColor
import com.example.jetnote.ui.navigation_drawer.NavigationDrawer
import kotlinx.coroutines.launch

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedMaterialScaffoldPaddingParameter"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    navC: NavController
) {
    val dataStore = DataStore(LocalContext.current)
    val isDarkTheme = dataStore.isDarkTheme.collectAsState(false)
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
                            searchLabel = null,
                            searchTitle = null
                        )
        },
        modifier = Modifier.systemBarsPadding()
    ){
        androidx.compose.material.Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = getMaterialColor(SURFACE),
            topBar = {
                SettingsTopAppBar(
                    drawerState = drawerState,
                    scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState
            ) {
                item {
                    SettingTemplateItem(
                        title =  { Text("Dark Mode", fontSize = 20.sp) },
                        description = { Text(
                            text = "",
                            fontSize = 12.sp,
                            modifier = Modifier.alpha(.7f)
                        ) },
                        icon = {
                            Icon(painterResource(OPACITY_ICON),null)
                        }
                    ) {
                        Switch(
                            modifier = Modifier,
                            checked = isDarkTheme.value,
                            onCheckedChange = {
                                scope.launch {
                                    dataStore.saveTheme(!isDarkTheme.value)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}