package city.zouitel.note.ui.utils

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.systemDesign.CommonBottomSheet
import city.zouitel.systemDesign.CommonConstants.DESCRIPTION_SIZE
import city.zouitel.systemDesign.CommonConstants.TITLE_SIZE
import city.zouitel.systemDesign.CommonOptionItem
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
data class RationalScreen(
    val permissionState: MultiplePermissionsState,
    val permissionName: String?
): Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navBottomSheet = LocalBottomSheetNavigator.current

        Navigator(CommonBottomSheet({
            Column(modifier =Modifier.padding(16.dp)) {
                Text(
                    text = "Permission",
                    fontSize = TITLE_SIZE.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text =
                    if (permissionState.revokedPermissions.size == 1) {
                        "You need $permissionName permission to contourne"
                    } else if (permissionState.revokedPermissions.first().permission == Manifest.permission.RECORD_AUDIO) {
                        "You need $permissionName permission. Please grant the permission."
                    } else {
                        throw Exception("There is no permission grant!")
                    },
                    fontSize = DESCRIPTION_SIZE.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(10.dp))

                CommonOptionItem(
                    onConfirm = {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        ContextCompat.startActivity(context, intent, null)
                        navBottomSheet.hide()
                    },
                    onDismiss = {
                        navBottomSheet.hide()
                    }
                )
            }
        }))
    }
}