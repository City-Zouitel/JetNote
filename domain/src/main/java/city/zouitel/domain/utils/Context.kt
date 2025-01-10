package city.zouitel.domain.utils

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

context(ScreenModel)
/**
 * Executes a suspending block of code within the `screenModelScope` asynchronously.
 *
 * This function provides a convenient way to launch asynchronous tasks within the lifecycle
 * of a screen model. It utilizes [async] to run the provided [scope] within the screen model's
 * coroutine scope.  Any exception thrown within the `scope` will be caught, wrapped in a new Exception
 * and rethrown.
 *
 * @param scope The suspending block of code to execute asynchronously. This lambda will be executed
 *              within the [screenModelScope].
 * @return A [Deferred] that completes when the [scope] lambda finishes executing.
 *         The result of the [Deferred] is [Unit], or it will throw an exception if the [scope] lambda failed.
 * @throws Exception if the provided [scope] lambda throws an exception. The original exception's message
 *                 will be wrapped within this new [Exception].
 */
fun withAsync(scope: suspend CoroutineScope.() -> Unit): Deferred<Unit> {
    return screenModelScope
        .async {
            runCatching {
                scope()
            }.onFailure {
                throw Exception(it.message)
            }
        }
}