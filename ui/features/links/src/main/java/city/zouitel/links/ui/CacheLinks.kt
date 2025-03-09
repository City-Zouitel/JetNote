@file:Suppress("DeferredResultUnused")

package city.zouitel.links.ui

import city.zouitel.domain.utils.ModelConstants.DEFAULT_TXT
import city.zouitel.links.model.Link
import com.devscion.metaprobekmp.MetaProbe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

fun cacheLink(url: String, uid: String) {
    val scope = CoroutineScope(Dispatchers.IO)
    val linkModel by inject<LinkScreenModel>(LinkScreenModel::class.java)
    val id by lazy { url.hashCode() }
    scope.launch {
        MetaProbe(url).probeLink().onSuccess {
            linkModel.doWork(
                Link(
                    id = id,
                    uid = uid,
                    title = it.title ?: DEFAULT_TXT,
                    description = it.description ?: DEFAULT_TXT,
                    image = it.image ?: DEFAULT_TXT,
                    url = url,
                    icon = it.icon ?: DEFAULT_TXT
                )
            )
        }
    }
}