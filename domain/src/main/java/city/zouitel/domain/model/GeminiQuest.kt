package city.zouitel.domain.model

import android.graphics.Bitmap
import androidx.annotation.Keep

@Keep
data class GeminiQuest(
    val image: Bitmap? = null,
    val prompt: String = "",
)
