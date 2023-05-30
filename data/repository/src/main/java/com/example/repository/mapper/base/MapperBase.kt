package com.example.repository.mapper.base

internal interface MapperBase<I, O> {
    fun toRepository(data: I): O
    fun toDomain(data: O): I
}