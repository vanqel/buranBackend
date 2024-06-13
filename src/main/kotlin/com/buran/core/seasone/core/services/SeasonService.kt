package com.buran.core.seasone.core.services

import com.buran.core.seasone.core.dto.SeasonCreateInput
import com.buran.core.seasone.core.dto.SeasonOutput
import com.buran.core.seasone.core.models.SeasonEntity
import com.buran.core.seasone.core.repository.ISeasonRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.springframework.stereotype.Service

@Service
class SeasonService(
    val repo: ISeasonRepository,
): ISeasonService {

    override fun createSeason(
        body: SeasonCreateInput,
    ): SeasonOutput {
        return repo.addNewSeason(body).toDTO()
    }
    override fun getList(): List<SeasonOutput> {
        return repo.getListSeason().map { it.toDTO() }
    }

    override fun deleteSeason(title: String): Boolean {
        return repo.deleteSeason(title)
    }

    override fun getSeasonFromTitle(title: String): SeasonEntity {
       return repo.getIdFromTitle(title)
    }
}


