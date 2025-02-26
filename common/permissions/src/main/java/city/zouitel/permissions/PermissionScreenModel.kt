package city.zouitel.permissions

import androidx.activity.ComponentActivity
import cafe.adriel.voyager.core.model.ScreenModel
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState.Denied
import dev.icerock.moko.permissions.PermissionState.Granted
import dev.icerock.moko.permissions.PermissionState.NotGranted
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.flow.flow

class PermissionScreenModel(
    private val controller: PermissionsController
): ScreenModel {

    fun activityBind(activity: ComponentActivity) {
        controller.bind(activity)
    }

    fun providePermission(permission: Permission) = flow {
        when (controller.getPermissionState(permission)) {
            NotGranted -> {
                runCatching {
                    controller.providePermission(permission)
                }.onFailure {
                    if (it.cause == null) emit(true)
                    else controller.openAppSettings()
                }.onSuccess {
                    if (controller.isPermissionGranted(permission)) emit(true)
                    else emit(false)
                }
            }
            Denied -> controller.openAppSettings()
            Granted -> emit(true)
            else -> controller.openAppSettings()
        }
    }
}