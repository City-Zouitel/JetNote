package com.example.mobile.ui.about_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.example.mobile.cons.SURFACE
import com.example.mobile.fp.getMaterialColor
import com.example.mobile.ui.navigation_drawer.NavigationDrawer
import com.example.mobile.ui.top_action_bar.CustomTopAppBar


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppAbout(
    navC: NavController
) {
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
        modifier = Modifier.navigationBarsPadding()
    ){
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = getMaterialColor(SURFACE),
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
                item { AboutContent() }
                item { AboutSources() }
            }
        }
    }

}
