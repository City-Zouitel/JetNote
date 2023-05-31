package com.example.repository.mapper.base

sealed interface Mapper {
    interface Base<In, Out> {

        fun toRepository(data: Out): In

        fun readOnly(data: In): Out
    }
    interface ReadOnly<In, Out> {

        fun readOnly(data: In): Out
    }
}