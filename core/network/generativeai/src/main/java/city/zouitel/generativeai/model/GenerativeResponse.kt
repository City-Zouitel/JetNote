package city.zouitel.generativeai.model

sealed class GenerativeResponse<out T> {
    data object Loading: GenerativeResponse<Nothing>()
    data class Success<out T>(val data: T): GenerativeResponse<T>()
    data class Failure(val e: Exception?): GenerativeResponse<Nothing>()
}
