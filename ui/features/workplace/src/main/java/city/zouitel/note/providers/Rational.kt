package city.zouitel.note.providers

import cafe.adriel.voyager.core.registry.ScreenProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
data class Rational(
    val permissionState: MultiplePermissionsState,
    val permissionName: String?
): ScreenProvider
