package city.zouitel.screens.tags_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.navigation_drawer.NavigationDrawer
import city.zouitel.systemDesign.CommonTopAppBar
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.tags.ui.TagScreenModel
import city.zouitel.tags.utils.DialogColors
import city.zouitel.tags.model.Tag as InTag

class HashTagsScreen: Screen {

    @Composable
    override fun Content() {

        Tags(
            datastoreModel = getScreenModel<DataStoreScreenModel>(),
            tagModel = getScreenModel<TagScreenModel>(),
            mainModel = getScreenModel<MainScreenModel>()
        )
    }

    @OptIn(
        ExperimentalComposeUiApi::class,
        ExperimentalMaterial3Api::class
    )
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter",
        "UnusedMaterialScaffoldPaddingParameter"
    )
     @Composable
    private fun Tags(
        datastoreModel: DataStoreScreenModel,
        tagModel: TagScreenModel,
        mainModel: MainScreenModel
    ) {
        val keyboardManager = LocalFocusManager.current
        val navigator = LocalNavigator.current

        val observeTags by remember(tagModel, tagModel::getAllLTags).collectAsState()
        val uiState by remember(tagModel, tagModel::uiState).collectAsState()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val topAppBarState = rememberTopAppBarState()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
        val scaffoldState = rememberScaffoldState()

        val focusRequester by lazy { FocusRequester() }

        if (uiState.isColorsDialog) {
            DialogColors(tagModel = tagModel)
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        ModalNavigationDrawer(
            drawerContent = {
                NavigationDrawer(
                    dataStoreModel = datastoreModel,
                    tagModel = tagModel,
                    drawerState = drawerState,
                    homeScreen = mainModel
                )
            },
            drawerState = drawerState,
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
                        title = "Tags"
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    item {
                        HashTagLayout(tagModel = tagModel)
                    }

                    item {
                        OutlinedTextField(
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .onFocusEvent { keyboardManager.moveFocus(FocusDirection.Enter) }
                                .clickable { navigator?.push(this@HashTagsScreen) },
                            value = uiState.currentLabel,
                            onValueChange = { tagModel.updateLabel(it) },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 19.sp,
                                color = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surface)
                            ),
                            placeholder = {
                                Text(
                                    text = "#Tag..",
                                    maxLines = 1,
                                    style = TextStyle(
                                        fontSize = 17.sp,
                                        color = Color.DarkGray
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (observeTags.any { it.id == uiState.currentId }) {
                                        tagModel.updateTag(
                                            InTag(
                                                id = uiState.currentId,
                                                label = uiState.currentLabel,
                                                color = uiState.currentColor
                                            )
                                        )
                                    } else {
                                        tagModel.addTag(
                                            InTag(
                                                label = uiState.currentLabel,
                                                color = uiState.currentColor
                                            )
                                        )
                                    }.invokeOnCompletion {
                                        tagModel.updateColor().updateId().updateLabel()
                                    }
                                }
                            ),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.DarkGray,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    }
}