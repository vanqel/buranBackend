package com.buran.core.players.repostiory

import com.buran.core.auth.errors.ValidationError
import com.buran.core.players.dto.PlayerCreateInput
import com.buran.core.players.dto.PlayerUpdateInput
import com.buran.core.players.models.PlayerEntity
import com.buran.core.players.models.PlayerTable
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
class PlayerRepository : IPlayerRepository {
    override fun createPlayer(body: PlayerCreateInput): PlayerEntity {
        val id = PlayerTable.insertAndGetId {
            it[name] = body.name
            it[biography] = body.biography
            it[number] = body.number
            it[photo] = UUID.fromString(body.photo)
            it[birthDate] = body.birthDate
            it[type] = body.type
            it[archived] = false
        }


        return getPlayer(id.value)!!
    }

    override fun updatePlayer(body: PlayerUpdateInput): PlayerEntity {
        val update =
            transaction {
                val a = PlayerTable.update(where = { PlayerTable.id eq body.id }) { p ->
                    body.name?.let { p[name] = it }
                    body.type?.let { p[type] = it }
                    body.number?.let { p[number] = it }
                    body.photo?.let { p[photo] = it }
                    body.biography?.let { p[biography] = it }
                    body.birthDate?.let { p[birthDate] = it }
                }
                commit()
                return@transaction a
            }
        if (update == 1) {
            return getPlayer(body.id)!!
        } else throw ValidationError("Ошибка обновления игрока")
    }

    override fun deletePlayer(id: Long): Boolean {
        val update =
            transaction {
                val a = PlayerTable.update(where = { PlayerTable.id eq id }) { p ->
                    p[archived] = true
                }
                commit()
                return@transaction a
            }
        if (update == 1) {
            return true
        } else throw ValidationError("Ошибка архивации игрока")
    }

    override fun getPlayer(id: Long): PlayerEntity? {
        return PlayerEntity.findById(id)
    }

    override fun getAllPlayers(): List<PlayerEntity> {
        return PlayerEntity.find { PlayerTable.archived eq false }.toList()
    }

    override fun getAllPlayersArchived(): List<PlayerEntity> {
        return PlayerEntity.find { PlayerTable.archived eq true }.toList()
    }


    override fun getPlayers(id: List<Long>): List<PlayerEntity> {
        return PlayerEntity.find {
            PlayerTable.id inList id
        }.toList()
    }

}
