package com.example.domain.repos

import com.example.local.model.Entity

interface WidgetEntityRepo {

    val getAllWidgetEntityById: List<Entity>
}