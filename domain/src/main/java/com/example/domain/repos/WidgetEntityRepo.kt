package com.example.domain.repos

import com.example.local.model.relational.MainEntity

interface WidgetEntityRepo {

    val getAllWidgetMainEntityById: List<MainEntity>
}