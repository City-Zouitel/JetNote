package city.zouitel.api

sealed class ApiResult<out R> {
    data class Success<out R>(val data: R): ApiResult<R>()
    data class Error(val message: String?): ApiResult<Nothing>()
    object Loading: ApiResult<Nothing>()
}