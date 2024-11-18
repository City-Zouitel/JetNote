package city.zouitel.applock

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import city.zouitel.systemDesign.DataStoreScreenModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LockModeHandler(datastoreModel: DataStoreScreenModel) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    val activity = LocalContext.current as AppCompatActivity

    val isLockMode by remember(datastoreModel, datastoreModel::isLockMode).collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val lockModeManager = LockModeManager(activity)

    LifecycleEventEffect(Lifecycle.Event.ON_START, lifecycleOwner) {
        scope.launch(Dispatchers.Main) {
            if (isLockMode) {
                lockModeManager.showAuthentication()
            }
            lockModeManager.resultFlow.collectLatest { authResult ->
                when (authResult) {
                    is LockModeManager.AuthResult.Error -> {
                        snackbarHostState.showSnackbar(authResult.message)
                    }

                    LockModeManager.AuthResult.Failed -> {
                        snackbarHostState.showSnackbar("Authentication failed")
                    }

                    LockModeManager.AuthResult.NoHardware, LockModeManager.AuthResult.HardwareUnavailable -> {
                        snackbarHostState.showSnackbar("No biometric Hardware available")
                    }

                    LockModeManager.AuthResult.Success -> {
                    }

                    LockModeManager.AuthResult.NoneEnrolled -> {
                        // User disabled biometric authentication
                        datastoreModel.setLockMode(false)
                    }
                }
            }
        }
    }
}