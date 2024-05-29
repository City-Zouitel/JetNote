package city.zouitel.links.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import city.zouitel.links.model.Link
import city.zouitel.logic.asShortToast
import java.util.*

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CacheLinks(
    linkScreenModel: LinkScreenModel,
    noteId: String,
    url: String
) {
    val context = LocalContext.current
    val observeLinks = remember(linkScreenModel, linkScreenModel::getAllLinks).collectAsState()

    val uiState by lazy { linkScreenModel.uiState }
    val id by lazy { Random().nextLong() }

    runCatching {
        linkScreenModel.urlPreview(context, url)?.fetchUrlPreview()
    }.onSuccess {
        if (
            observeLinks.value.none { it.url == url } &&
            uiState.title.isNotBlank() &&
            uiState.host.isNotBlank() &&
            uiState.img.isNotBlank()
        ) {
            linkScreenModel.doWork(
                noteId = noteId,
                link = Link(id, url, uiState.host, uiState.img, uiState.title, uiState.description)
            )
        }
    }.onFailure {
        context.apply { it.message?.asShortToast() }
    }
}