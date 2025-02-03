package city.zouitel.domain.utils

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

context(ScreenModel)
/**
 * Launches a new coroutine within the [screenModelScope] and returns its result as a [Deferred].
 *
 * This function is a convenient wrapper around [screenModelScope.async] that automatically
 * uses the `screenModelScope` as the coroutine context. It is primarily used for launching
 * asynchronous tasks that need to be managed within the lifecycle of the screen model.
 *
 * The coroutine will be cancelled automatically when the [screenModelScope] is cancelled.
 *
 * @param scope The coroutine code block to execute asynchronously.
 *              This block is executed within a newly created coroutine on the `screenModelScope`.
 * @return A [Deferred] representing the result of the asynchronous computation.
 *         You can use `await()` on the returned `Deferred` to retrieve the result once it's ready.
 *
 * @see kotlinx.coroutines.async
 * @see kotlinx.coroutines.Deferred
 * @see screenModelScope
 */
fun withAsync(scope: suspend CoroutineScope.() -> Unit): Deferred<Unit> {
    return screenModelScope.async(block = scope)
}
