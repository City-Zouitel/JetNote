package city.zouitel.root.mapper.base

sealed interface Mapper {
    interface Base<In, Out> {

        fun toView(data: Out): In

        fun toDomain(data: In): Out
    }
    interface ReadOnly<Out, In> {

        fun toView(data: Out): In
    }
}