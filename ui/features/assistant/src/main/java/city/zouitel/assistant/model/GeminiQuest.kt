package city.zouitel.assistant.model

import android.graphics.Bitmap
import androidx.annotation.Keep
import city.zouitel.assistant.OperationType

@Keep
data class GeminiQuest(
    val image: Bitmap? = null,
    val prompt: String = "",
    val type: OperationType = OperationType.REQUEST
)
