package city.zouitel.systemDesign

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RationalDialog(
    showRationalDialog: MutableState<Boolean>,
    permissionState: MultiplePermissionsState,
    permissionName: String?
) {
    val context = LocalContext.current
    if (showRationalDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showRationalDialog.value = false
            },
            title = {
                Text(text = "Permission")
            },
            text = {
                Text(
                    if (permissionState.revokedPermissions.size == 1) {
                        "You need $permissionName permission to contourne"
                    } else if (permissionState.revokedPermissions.first().permission == Manifest.permission.RECORD_AUDIO) {
                        "You need $permissionName permission. Please grant the permission."
                    } else {
                        throw Exception("There is no permission grant!")
                    }
                )
            },
            confirmButton = {
                OutlinedIconButton(
                    modifier = Modifier
                        .size(90.dp, 35.dp),
                    onClick = {
                        showRationalDialog.value = false
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        ContextCompat.startActivity(context, intent, null)
                    }) {
                    Text(text = "Settings", fontSize = 16.sp)
                }
            },
            dismissButton = {
                OutlinedIconButton(
                    modifier = Modifier
                        .size(90.dp, 35.dp),
                    onClick = {
                        showRationalDialog.value = false
                    }) {
                    Text(text = "Cancel", fontSize = 16.sp)
                }
            },
        )
    }
}