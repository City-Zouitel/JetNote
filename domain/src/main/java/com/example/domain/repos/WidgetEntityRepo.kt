package com.example.domain.repos

import com.example.local.model.relational.Entity

interface WidgetEntityRepo {

    val getAllWidgetEntityById: List<Entity>
}