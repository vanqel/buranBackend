package com.buran.core.players.models

import com.buran.core.config.ExtendedLongEntity
import com.buran.core.players.dto.PlayerFullOutput
import com.buran.core.players.dto.PlayerSimpleOutput
import com.buran.core.seasone.core.models.SeasonEntity
import com.buran.core.seasone.matches.models.tables.MatchTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PlayerEntity(id: EntityID<Long>): ExtendedLongEntity(id, PlayerTable) {
    companion object: LongEntityClass<PlayerEntity>(PlayerTable)
    var name by PlayerTable.name
    var biography by PlayerTable.biography
    var number by PlayerTable.number
    var photo by PlayerTable.photo
    var birthDate by PlayerTable.birthDate
    var archived by PlayerTable.archived
    var type by PlayerTable.type

    fun toSimpleOutput(urlPh: String?): PlayerSimpleOutput {
        return PlayerSimpleOutput(
            id = id.value,
            name = name,
            number = number,
            type = type,
            url = urlPh
        )
    }

    fun toFullOutput(urlPh: String?): PlayerFullOutput {
        return PlayerFullOutput(
            name = name,
            number = number,
            biography = biography,
            birthDate = birthDate.toString(),
            type = type,
            url = urlPh
        )
    }
}
