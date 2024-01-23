package city.zouitel.repository.mapper.base

sealed interface Mapper {
    interface Base<In, Out> {

        fun toRepository(data: Out): In

        fun toDomain(data: In): Out
    }
    interface ReadOnly<In, Out> {

        fun toDomain(data: In): Out
    }
}