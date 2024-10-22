package city.zouitel.logic

sealed class UiEvent<T> {
    data class Insert<T> (val data: T): UiEvent<T>()
    data class Update<T> (val data: T): UiEvent<T>()
    data class Delete<T> (val data: T): UiEvent<T>()
}