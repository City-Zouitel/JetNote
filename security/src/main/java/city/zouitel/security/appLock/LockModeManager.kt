package city.zouitel.security.appLock

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LockModeManager(private val activity: AppCompatActivity) {

    private val biometricManager = BiometricManager.from(activity)

    private val resultChannel = Channel<AuthResult>()
    val resultFlow = resultChannel.receiveAsFlow()

    private val authenticators = if (Build.VERSION.SDK_INT >= 30) {
        BIOMETRIC_WEAK or DEVICE_CREDENTIAL
    } else BIOMETRIC_WEAK

    fun showAuthentication() {
        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle(Constants.TITLE)
            .setDescription(Constants.DESCRIPTION)
            .setConfirmationRequired(false)
            .setAllowedAuthenticators(authenticators)
        if (Build.VERSION.SDK_INT < 30) info.setNegativeButtonText("Cancel")

        when (biometricManager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                resultChannel.trySend(AuthResult.NoHardware)
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                resultChannel.trySend(AuthResult.HardwareUnavailable)
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultChannel.trySend(AuthResult.NoneEnrolled)
            }
            else -> Unit
        }
        val prompt = BiometricPrompt(
            activity,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
//                        exitProcess(0)
                        activity.finish()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    resultChannel.trySend(AuthResult.Success)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    resultChannel.trySend(AuthResult.Failed)
                }
            }
        )
        prompt.authenticate(info.build())
    }

    fun isAvailable(): Boolean {
        return biometricManager.canAuthenticate(authenticators) == BiometricManager.BIOMETRIC_SUCCESS
    }

    sealed interface AuthResult {
        data object NoneEnrolled: AuthResult
        data object HardwareUnavailable: AuthResult
        data object NoHardware: AuthResult
        data class Error(val message: String): AuthResult
        data object Success: AuthResult
        data object Failed: AuthResult
    }
}