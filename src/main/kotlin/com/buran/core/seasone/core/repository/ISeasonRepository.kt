package com.buran.core.seasone.core.repository

import com.buran.core.seasone.core.dto.SeasonCreateInput
import com.buran.core.seasone.core.models.SeasonEntity
import org.jetbrains.exposed.dao.id.EntityID

interface ISeasonRepository {
    fun getListSeason(): List<SeasonEntity>

    fun addNewSeason(body: SeasonCreateInput): SeasonEntity

    fun deleteSeason(id: Long): Boolean

    fun getIdFromTitle(title: String): EntityID<Long>
}
