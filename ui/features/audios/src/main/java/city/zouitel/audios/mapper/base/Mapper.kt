package city.zouitel.audios.mapper.base

sealed interface Mapper {
    interface Base<In, Out> {

        fun toView(data: Out): In

        fun toDomain(data: In): Out
    }
    interface ReadOnly<In, Out> {

        fun toView(data: In): Out
    }
}