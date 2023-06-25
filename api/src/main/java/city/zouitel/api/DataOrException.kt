package city.zouitel.api

data class DataOrException<T, E : Exception?>(
    var data: T? = null,
    var e: E? = null
)