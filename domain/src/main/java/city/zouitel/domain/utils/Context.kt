package city.zouitel.domain.utils

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

context(ScreenModel)
fun exe(scope: suspend CoroutineScope.() -> Unit) = screenModelScope
    .launch(context = Dispatchers.IO, block = scope)