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
    linkModel: LinkScreenModel,
    noteId: String,
    url: String
) {
    val context = LocalContext.current
    val observeLinks = remember(linkModel, linkModel::getAllLinks).collectAsState()
    val uiState by remember(linkModel, linkModel::uiState).collectAsState()

    val id by lazy { Random().nextLong() }

    runCatching {
        linkModel.urlPreview(context, url)?.fetchUrlPreview()
    }.onSuccess {
        if (
            observeLinks.value.none { it.url == url } &&
            uiState.title.isNotBlank() &&
            uiState.host.isNotBlank() &&
            uiState.img.isNotBlank()
        ) {
            linkModel.doWork(
                noteId = noteId,
                link = Link(id, url, uiState.host, uiState.img, uiState.title, uiState.description)
            )
        }
    }.onFailure {
        context.apply { it.message?.asShortToast() }
    }
}