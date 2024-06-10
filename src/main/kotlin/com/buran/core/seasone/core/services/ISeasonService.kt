package com.buran.core.seasone.core.services

import com.buran.core.seasone.core.dto.SeasonCreateInput
import com.buran.core.seasone.core.dto.SeasonOutput
import org.jetbrains.exposed.dao.id.EntityID

interface ISeasonService {
    fun createSeason(body: SeasonCreateInput): SeasonOutput
    fun getList(): List<SeasonOutput>
    fun deleteSeason(id: Long): Boolean
    fun getSeasonFromTitle(title: String): EntityID<Long>
}
