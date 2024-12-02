package city.zouitel.domain.utils

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable

sealed class RequestState<out T> {
    data object Idle : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()
    data class Success(val data: String) : RequestState<String>()
    data class Error(val message: String) : RequestState<Nothing>()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error

    /**
     * Returns data from a [Success].
     * @throws ClassCastException If the current state is not [Success]
     *  */
    private fun getSuccessData() = (this as Success).data
    fun getSuccessDataOrNull(): String? {
        return try {
            (this as Success).data
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Returns an error message from an [Error]
     * @throws ClassCastException If the current state is not [Error]
     *  */
    private fun getErrorMessage() = (this as Error).message
    fun getErrorMessageOrNull(): Result<String> {
        return runCatching {
            (this as Error).message
        }.onFailure {
            it.message
        }
    }

    @Composable
    fun DisplayResult(
        onIdle: (@Composable () -> Unit)? = null,
        onLoading: @Composable () -> Unit,
        onSuccess: @Composable (String) -> Unit,
        onError: @Composable (String) -> Unit,
    ) {
        AnimatedContent(
            targetState = this,
            transitionSpec = {
                fadeIn(tween(durationMillis = 300)) togetherWith
                        fadeOut(tween(durationMillis = 300))
            },
            label = "Content Animation"
        ) { state ->
            when (state) {
                is Idle -> { onIdle?.invoke() }
                is Loading -> { onLoading() }
                is Success -> { onSuccess(state.getSuccessData()) }
                is Error -> { onError(state.getErrorMessage()) }
            }
        }
    }
}