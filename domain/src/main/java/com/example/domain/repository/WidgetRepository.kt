package com.example.domain.repository

import com.example.domain.model.Note as OutNote


interface WidgetRepository {

    val getAllWidgetMainEntityById: List<OutNote>
}