package city.zouitel.screens.app_lock_screen

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import city.zouitel.security.appLock.LockModeManager

class AppLockScreen : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val activity = context as AppCompatActivity
        val lockModeManager = LockModeManager(activity)
        AppLock(lockModeManager)
    }
}
@Composable
private fun AppLock(lockModeManager: LockModeManager?) {

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "title",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { lockModeManager?.showAuthentication() },
            shape = RoundedCornerShape(99.dp),
        ) {
            Text(
                text = "button",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
