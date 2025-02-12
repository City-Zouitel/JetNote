package city.zouitel.recoder.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.domain.utils.Action
import city.zouitel.recoder.model.Record
import city.zouitel.systemDesign.CommonBottomSheet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import kotlin.random.Random
import kotlin.time.DurationUnit

class RecorderScreen(val uid: String, private val random: Int = Random.nextInt()): Screen {
    @Composable
    override fun Content() {
        Navigator(CommonBottomSheet {
            Recorder(getScreenModel())
        })
    }

    @Composable
    private fun Recorder(recorderModel: RecorderScreenModel) {
        val context = LocalContext.current
        val navBottom = LocalBottomSheetNavigator.current
        val scope = rememberCoroutineScope()
        val uiState by remember(recorderModel, recorderModel::uiState).collectAsState()
        val file = File(context.filesDir.path + "/" + random.toString() + ".mp3")

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(20.dp))

            RecordTimer(uiState.seconds, uiState.minutes, uiState.hours)

            RecordController(
                recorderModel = recorderModel,
                onStart = {
                    recorderModel.start(file)
                },
                onPause = recorderModel::pause,
                onResume = recorderModel::resume,
                onStop = {
                    recorderModel.stop()
                    recorderModel.sendAction(
                        Action.Insert(
                            Record(
                                uid = uid,
                                path = file.path,
                                duration = recorderModel.time.toLong(DurationUnit.MILLISECONDS)
                            )
                        )
                    )
                    recorderModel.reset()
                    scope.launch {
                        delay(400)
                        navBottom.hide()
                    }
                }
            )

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}