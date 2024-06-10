package com.buran.core.seasone.core.repository

import com.buran.core.seasone.core.dto.SeasonCreateInput
import com.buran.core.seasone.core.models.SeasonEntity
import com.buran.core.seasone.core.models.SeasonTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class SeasonRepository : ISeasonRepository {
    override fun getListSeason(): List<SeasonEntity> {
        return SeasonEntity.all().toList()
    }

    override fun addNewSeason(body: SeasonCreateInput): SeasonEntity {
        return SeasonEntity.new {
            title = "${body.dateStart.year}-${body.dateEnd.year}"
            dateStart = body.dateStart
            dateEnd = body.dateEnd
        }
    }

    override fun deleteSeason(id: Long): Boolean {
        return SeasonTable.deleteWhere { SeasonTable.id eq id } == 1
    }

    override fun getIdFromTitle(title: String): EntityID<Long> {
        return SeasonEntity.find { SeasonTable.title eq title }.first().id
    }

}
