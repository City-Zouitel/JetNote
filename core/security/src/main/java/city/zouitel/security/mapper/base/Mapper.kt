package city.zouitel.security.mapper.base

sealed interface Mapper {
    interface Base<In, Out> {
        fun toLocal(data: Out): In
        fun readOnly(data: In): Out
    }
    interface ReadOnly<In, Out> {
        fun readOnly(data: In): Out
    }
}