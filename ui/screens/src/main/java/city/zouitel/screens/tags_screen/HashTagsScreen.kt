package city.zouitel.screens.tags_screen

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.domain.utils.Action
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.navigation_drawer.NavigationDrawer
import city.zouitel.systemDesign.CommonTextField
import city.zouitel.systemDesign.CommonTopAppBar
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.tags.model.Tag
import city.zouitel.tags.ui.NoteAndTagScreenModel
import city.zouitel.tags.ui.TagScreenModel

class HashTagsScreen: Screen {

    @Composable
    override fun Content() {
        Tags(
            datastoreModel = getScreenModel(),
            tagModel = getScreenModel(),
            noteAndTagModel = getScreenModel(),
            mainModel = getScreenModel()
        )
    }

    @OptIn(
        ExperimentalComposeUiApi::class,
        ExperimentalMaterial3Api::class
    )
    @SuppressLint(
        "UnusedMaterial3ScaffoldPaddingParameter",
        "UnusedMaterialScaffoldPaddingParameter"
    )
    @Composable
    private fun Tags(
        datastoreModel: DataStoreScreenModel,
        tagModel: TagScreenModel,
        noteAndTagModel: NoteAndTagScreenModel,
        mainModel: MainScreenModel
    ) {
        val keyboardManager = LocalFocusManager.current

        val uiState by remember(tagModel, tagModel::uiState).collectAsState()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val topAppBarState = rememberTopAppBarState()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
        val scaffoldState = rememberScaffoldState()

        val focusRequester by lazy { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                NavigationDrawer(
                    dataStoreModel = datastoreModel,
                    tagModel = tagModel,
                    drawerState = drawerState,
                    mainModel = mainModel
                )
            },
            modifier = Modifier.navigationBarsPadding()
        ) {
            androidx.compose.material.Scaffold(
                scaffoldState = scaffoldState,
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    CommonTopAppBar(
                        datastoreModel = datastoreModel,
                        drawerState = drawerState,
                        scrollBehavior = scrollBehavior,
                        title = "#Tags"
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    item {
                        HashTagLayout(tagModel, noteAndTagModel)
                    }

                    item {
                        CommonTextField(
                            value = uiState.currentLabel,
                            onValueChange = { tagModel.updateLabel(it) },
                            receiver = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester)
                                .onFocusEvent { keyboardManager.moveFocus(FocusDirection.Enter) },
                            placeholder = "#Tag..",
                            imeAction = ImeAction.Done,
                            keyboardActions = KeyboardActions(onDone = {
                                tagModel.sendAction(
                                    Action.Insert(
                                        data = Tag(
                                            id = uiState.currentId,
                                            label = uiState.currentLabel,
                                            color = uiState.currentColor
                                        )
                                    )
                                )
                                tagModel.updateColor().updateId().updateLabel()
                            })
                        )
                    }
                }
            }
        }
    }
}