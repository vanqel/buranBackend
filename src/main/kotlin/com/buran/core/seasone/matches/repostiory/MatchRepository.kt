package com.buran.core.seasone.matches.repostiory

import com.buran.core.seasone.matches.dto.MatchCreateInput
import com.buran.core.seasone.matches.dto.MatchCreateOutput
import com.buran.core.seasone.matches.dto.MatchResult
import com.buran.core.seasone.matches.models.MatchEntity
import com.buran.core.seasone.matches.models.MatchResultEntity
import com.buran.core.seasone.matches.models.TeamsMatchEntity
import com.buran.core.seasone.matches.models.tables.MatchResultTable
import com.buran.core.seasone.matches.models.tables.MatchTable
import com.buran.core.seasone.matches.models.tables.MatchTeams
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class MatchRepository : IMatchRepository {
    override fun getMatchesBySeason(seasonId: Long): List<MatchCreateOutput?> {
        return MatchEntity.find { MatchTable.season eq seasonId }.toList().map {
            MatchCreateOutput(
                id = it.id.value,
                enemy = it.enemy,
                dateStart = it.dateTimeStart,
                team = TeamsMatchEntity.find { MatchTeams.match eq it.id }.first()
                    .let { it.teamId.map { it.id.value } },
                title = it.title
            )
        }
    }

    override fun getMatch(id: Long): MatchCreateOutput? {
        return MatchEntity.findById(id)?.let {
            MatchCreateOutput(
                id = it.id.value,
                enemy = it.enemy,
                dateStart = it.dateTimeStart,
                team = TeamsMatchEntity.find { MatchTeams.match eq it.id }.first()
                    .let { it.teamId.map { it.id.value } },
                title = it.title
            )
        }
    }

    override fun createMatch(s: Long, body: MatchCreateInput): MatchCreateOutput {
        return transaction {
            val a = MatchTable.insertAndGetId {
                it[season] = s
                it[title] = body.title
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
                title = body.title
            )
        }
    }

    override fun updateMatch(id: Long, body: MatchCreateInput): MatchCreateOutput {
        return transaction {
            MatchTable.update({ MatchTable.id eq id }) {
                it[title] = body.title
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
                title = body.title
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
            }
            commit()
        }
        return getMatch(id)!!
    }

    override fun getMatchResults(id: Long): List<MatchResultEntity?> {
        return MatchResultEntity.find { MatchResultTable.matchId eq id }.toList()
    }
}
