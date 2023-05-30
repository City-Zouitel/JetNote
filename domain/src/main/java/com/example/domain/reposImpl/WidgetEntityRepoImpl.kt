package com.example.domain.reposImpl

import com.example.domain.repos.WidgetEntityRepo
import com.example.local.dao.WidgetEntityDao
import com.example.local.model.relational.MainEntity
import javax.inject.Inject

class WidgetEntityRepoImpl @Inject constructor(
    private val dao: WidgetEntityDao
):WidgetEntityRepo {

    val getAllWidgetMainEntityById: List<MainEntity>
        get() = dao.allWidgetEntitiesOrderedById()
}