package city.zouitel.logic.events

sealed class UiEvent<out T> {
    data class Insert<out T>(val data: T) : UiEvent<T>()
    data class Update<out T>(val data: T) : UiEvent<T>()
    data class UpdateById<out T>(val data: T) : UiEvent<T>()
    data class Delete<out T>(val data: T) : UiEvent<T>()
    class DeleteAll<out T> : UiEvent<T>()
}

sealed class UiEvents {
    data class Insert<out T>(val data: T) : UiEvents()
    data class Update<out T>(val data: T) : UiEvents()
    data class Delete<out T> (val data: T) : UiEvents()
    class DeleteAll<out T> : UiEvents()
}