package com.example.domain.reposImpl

import com.example.domain.repos.WidgetEntityRepo
import com.example.local.daos.WidgetEntityDao
import com.example.local.model.relational.Entity
import javax.inject.Inject

class WidgetEntityRepoImpl @Inject constructor(
    private val dao: WidgetEntityDao
):WidgetEntityRepo {

    override val getAllWidgetEntityById: List<Entity>
        get() = dao.allWidgetEntitiesOrderedById()
}