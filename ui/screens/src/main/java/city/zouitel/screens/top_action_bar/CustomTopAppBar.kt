package city.zouitel.screens.top_action_bar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.DataStoreScreenModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    dataStoreModel: DataStoreScreenModel,
    drawerState: DrawerState,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    title: String
) {
    TopAppBar(
        navigationIcon = {
            Row {
                CommonRow(
                    Modifier.padding(start = 10.dp, end = 10.dp),
                ) {
                    Open_Drawer(dataStoreModel = dataStoreModel, drawerState = drawerState)
                }
            }
        },
        title = { Text(title, fontSize = 22.sp, modifier = Modifier.padding(start = 15.dp)) },
        scrollBehavior = topAppBarScrollBehavior
    )
}