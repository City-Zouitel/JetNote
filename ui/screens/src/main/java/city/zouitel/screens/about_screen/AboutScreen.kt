package city.zouitel.screens.about_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.screens.R
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.navigation_drawer.NavigationDrawer
import city.zouitel.screens.utils.sound
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonTopAppBar
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.tags.ui.TagScreenModel
import coil.compose.AsyncImage

class AboutScreen: Screen {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        About(
            datastoreModel = getScreenModel(),
            tagModel = getScreenModel(),
            homeScreen = getScreenModel()
        )
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun About(
        datastoreModel: DataStoreScreenModel,
        tagModel: TagScreenModel,
        homeScreen: MainScreenModel
    ) {
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
                    mainModel = homeScreen
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
                    item { ProjectSources(dataStoreModel = datastoreModel) }
                }
            }
        }
    }

    companion object {
        const val ABOUT = "${CommonConstants.APP_NAME} is a note-taking and todo management open-source application.\n\n" +
                " It is developed by City-Zouitel organization on github.\n\n" +
                " And It is intended for archiving and creating notes in which photos," +
                " audio and saved web links, numbers and map locations.\n\n" +
                "Version: ${CommonConstants.APP_VERSION}"
    }

    @Composable
    private fun AboutDescription() {
        Text(
            text = ABOUT,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(20.dp)
        )
    }

    @Composable
    private fun AboutLabel() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            AsyncImage(
                model = R.drawable.mat,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .blur(
                        radius = 4.dp,
                        edgeTreatment = BlurredEdgeTreatment.Rectangle
                    )
            )
            Text(
                text = CommonConstants.APP_NAME,
                fontSize = 60.sp,
                color = Color.White
            )
        }
    }

    @Composable
    private fun ProjectSources(dataStoreModel: DataStoreScreenModel) {

        val uri = LocalUriHandler.current
        val context = LocalContext.current
        val isMute by remember(dataStoreModel, dataStoreModel::isMute).collectAsState()

        Opensource(
            text = "Project Source",
            icon = {
                Icon(
                    painter = painterResource(CommonIcons.GITHUB_ICON),
                    contentDescription = ""
                )
            }
        ) {
            sound.performSoundEffect(context, CommonConstants.KEY_CLICK, isMute)
            uri.openUri("https://github.com/City-Zouitel/JetNote")
        }
    }

    @Composable
    private fun Opensource(
        text: String,
        icon: @Composable () -> Unit,
        action: () -> Unit
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(.5f)
                .padding(50.dp),
            onClick = action,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
            ) {
                icon.invoke()
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = text,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

