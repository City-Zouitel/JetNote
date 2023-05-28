package com.example.local.mapper.base

interface MapperBase<I, O> {
    fun toLocal(data: I): O
    fun toRepository(data: O): I
}