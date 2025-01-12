package city.zouitel.domain.utils

sealed class Action {
    data class Insert<out T>(val data: T) : Action()
    data class Update<out T>(val data: T) : Action()
    data class UpdateById(val id: Number) : Action()
    data class Delete<out T> (val data: T) : Action()
    data class DeleteById (val id: Number): Action()
    data class DeleteByUid (val uid: String): Action()
    data object DeleteAll : Action()
}