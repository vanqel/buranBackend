package com.buran.core.seasone.matches.repostiory

import com.buran.core.auth.errors.ValidationError
import com.buran.core.extensions.selectAllNotDeleted
import com.buran.core.seasone.core.models.SeasonEntity
import com.buran.core.seasone.core.models.SeasonTable
import com.buran.core.seasone.matches.dto.MatchCreateInput
import com.buran.core.seasone.matches.dto.MatchCreateOutput
import com.buran.core.seasone.matches.dto.MatchResult
import com.buran.core.seasone.matches.models.MatchEntity
import com.buran.core.seasone.matches.models.MatchResultEntity
import com.buran.core.seasone.matches.models.TeamsMatchEntity
import com.buran.core.seasone.matches.models.tables.MatchResultTable
import com.buran.core.seasone.matches.models.tables.MatchTable
import com.buran.core.seasone.matches.models.tables.MatchTeams
import com.fasterxml.jackson.databind.ObjectMapper
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class MatchRepository(
    val objMapper: ObjectMapper,
) : IMatchRepository {
    override fun getMatchesBySeason(seasonId: Long): List<MatchCreateOutput?> {
        return MatchEntity.find { MatchTable.season eq seasonId }.toList().ifEmpty { listOf() }.toList().map { m ->
            MatchCreateOutput(
                id = m.id.value,
                enemy = m.enemy,
                dateStart = m.dateTimeStart,
                team = TeamsMatchEntity.find { MatchTeams.match eq m.id }.map { it.teamId.value },
            )
        }
    }

    override fun getMatch(id: Long): MatchCreateOutput {
        return MatchEntity.findById(id)?.let {
            MatchCreateOutput(
                id = it.id.value,
                enemy = it.enemy,
                dateStart = it.dateTimeStart,
                team = TeamsMatchEntity.find { MatchTeams.match eq it.id }.map { it.teamId.value },
            )
        } ?: throw ValidationError("Матч не найден")
    }

    override fun createMatch(s: Long, body: MatchCreateInput): MatchCreateOutput {
        return transaction {
            val a = MatchTable.insertAndGetId {
                it[season] = s
                it[enemy] = body.enemy
                it[dateTimeStart] = body.dateTimeStart
            }
            commit()
            MatchTeams.batchInsert(body.team) {
                this[MatchTeams.match] = a
                this[MatchTeams.team] = it
            }
            commit()

            return@transaction MatchCreateOutput(
                id = a.value,
                enemy = body.enemy,
                dateStart = body.dateTimeStart,
                team = body.team,
            )
        }
    }

    override fun updateMatch(id: Long, body: MatchCreateInput): MatchCreateOutput {
        return transaction {
            MatchTable.update({ MatchTable.id eq id }) {
                it[enemy] = body.enemy
                it[dateTimeStart] = body.dateTimeStart
            }
            commit()
            body.team.forEach { p ->
                MatchTeams.update({ MatchTeams.match eq id }) {
                    it[match] = id
                    it[team] = p
                }
            }
            commit()
            return@transaction MatchCreateOutput(
                id = id,
                enemy = body.enemy,
                dateStart = body.dateTimeStart,
                team = body.team,
            )
        }
    }

    override fun deleteMatch(id: Long) {
        transaction {
            MatchTable.deleteWhere { MatchTable.id eq id }
            commit()
        }
    }

    override fun updateMatchResult(id: Long, body: MatchResult): MatchCreateOutput {
        transaction {
            MatchResultTable.insertAndGetId {
                it[matchId] = id
                it[playerId] = body.playerId
                it[action] = body.action
                it[minutes] = body.minutes
                it[enemy] = body.enemy
            }

            commit()
        }
        return getMatch(id)
    }

    override fun getMatchResults(id: Long): List<MatchResultEntity?> {
        return MatchResultEntity.find { MatchResultTable.matchId eq id }.toList()
    }
}
