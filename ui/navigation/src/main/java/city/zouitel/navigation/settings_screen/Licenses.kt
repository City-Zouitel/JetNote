package city.zouitel.navigation.settings_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.LibraryDefaults

class Licenses: Screen {
    @Composable
    override fun Content() {
            Scaffold(
                content = { paddingValues -> OpenSourceContent(modifier = Modifier.padding(paddingValues)) }
            )
    }
    @Composable
    private fun OpenSourceContent(modifier: Modifier = Modifier) {
        LibrariesContainer(
            modifier = modifier.fillMaxSize(),
            colors = LibraryDefaults.libraryColors(
                backgroundColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                badgeContentColor = MaterialTheme.colorScheme.onPrimary,
                badgeBackgroundColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}