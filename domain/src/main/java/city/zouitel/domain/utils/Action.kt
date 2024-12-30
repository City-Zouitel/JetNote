package city.zouitel.domain.utils

sealed class Action {
    data class Insert<out T>(val data: T) : Action()
    data class Update<out T>(val data: T) : Action()
    data class Delete<out T> (val data: T) : Action()
    data object DeleteAll : Action()
}