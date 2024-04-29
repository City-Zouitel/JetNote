package city.zouitel.quicknote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import city.zouitel.quicknote.ui.QuickScreen
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.MainTheme
import org.koin.android.ext.android.inject

class QuickActivity: ComponentActivity() {

    private val dataStoreModel by inject<DataStoreScreenModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme(dataStoreModel) {
                Navigator(screen = QuickScreen { finish() })
            }
        }
    }
}

