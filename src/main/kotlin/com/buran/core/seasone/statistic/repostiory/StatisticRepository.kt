package com.buran.core.seasone.statistic.repostiory

import com.buran.core.auth.errors.GeneralError
import com.buran.core.extensions.isNotNull
import com.buran.core.extensions.isNull
import com.buran.core.players.services.PlayerService
import com.buran.core.seasone.core.services.SeasonService
import com.buran.core.seasone.matches.enums.MatchAction
import com.buran.core.seasone.matches.models.tables.MatchTable
import com.buran.core.seasone.matches.models.tables.MatchTeams
import com.buran.core.seasone.statistic.dto.*
import com.buran.core.seasone.statistic.models.ManualTable
import com.buran.core.seasone.statistic.models.MatchResultStatsView
import com.buran.core.seasone.statistic.models.PlayerStatsView
import com.buran.core.seasone.statistic.models.SeasonStatsView
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
class StatisticRepository(
    val playerService: PlayerService,
    val seasonService: SeasonService,
) : IStatisticRepository {

    override fun getStatisticPlayersBySeason(season: String): List<StatisticPlayer?> {

        val s = seasonService.getSeasonFromTitle(season)

        val playersBySeason = MatchTable.join(
            MatchTeams, joinType = JoinType.LEFT
        ).select(MatchTeams.team).where {
            MatchTable.season eq s.id
        }.map {
            it[MatchTeams.team].value
        }.toSet()

        val act = PlayerStatsView.selectAll().where {
            PlayerStatsView.title eq season
        }

        val actions = act.map { row ->
            val pl=  row[PlayerStatsView.playerId]!!.value
            val acts = row[PlayerStatsView.action]
            PlayerStatsRaw(
                pl,
                row[PlayerStatsView.matchId].value,
                acts,
                act.filter {
                    it[PlayerStatsView.playerId]!!.value == pl && it[PlayerStatsView.action] == acts
                }.groupBy { it[PlayerStatsView.action] }.size.toLong(),
                row[PlayerStatsView.title],
            )
        }.toMutableList()

        val playersAllPlayed: MutableList<Long> = mutableListOf()


        actions.forEach {
            playersAllPlayed.add(it.playerId)
        }


        playersBySeason.filter { it !in playersAllPlayed.toSet() }.forEach {
            actions.add(PlayerStatsRaw(it, 0, MatchAction.GOAL, 0, season))
        }

        val players = actions.groupBy {
            it.playerId
        }.map { entry ->
            val player = playerService.getPlayerSimple(entry.key)
            val matchMap: EnumMap<MatchAction, Long> = EnumMap(MatchAction::class.java)
            MatchAction.entries.forEach { e ->
                matchMap[e] = 0
            }
            entry.value.forEach {
                matchMap[it.action] = matchMap[it.action]?.plus(it.count) ?: it.count
            }
            StatisticPlayer(player, matchMap)
        }

        return players
    }

    override fun getStatisticMatchBySeason(match: Long): StatisticMatch {
        val act = PlayerStatsView.selectAll().where {
            PlayerStatsView.matchId eq match
        }.filter {
            if (it.isNull()) {
                throw GeneralError("Матч не найден")
            }
            it.isNotNull()
        }

        val actions = act.map { row ->
            val pl=  row[PlayerStatsView.playerId]!!.value
            val acts = row[PlayerStatsView.action]
            PlayerStatsRaw(
                pl,
                row[PlayerStatsView.matchId].value,
                acts,
                act.filter {
                    it[PlayerStatsView.playerId]!!.value == pl && it[PlayerStatsView.action] == acts
                }.groupBy { it[PlayerStatsView.action] }.size.toLong(),
                row[PlayerStatsView.title],
            )
        }
        val matchMap: EnumMap<MatchAction, Long> = EnumMap(MatchAction::class.java)
        MatchAction.entries.forEach { e ->
            matchMap[e] = 0
        }

        actions.map {
            matchMap[it.action] = matchMap[it.action]?.plus(it.count) ?: it.count
        }
        return StatisticMatch(
            getStatsPlayersFromMatch(match).last()?.toStatisticPlayer(),
            matchMap.toMap()
        )
    }

    fun getStatsPlayersFromMatch(mId: Long): List<StatisticPlayerScore?> {
        val act = PlayerStatsView.selectAll().where {
            PlayerStatsView.matchId eq mId
        }

        val actions = act.map { row ->
            val pl=  row[PlayerStatsView.playerId]!!.value
            val acts = row[PlayerStatsView.action]
            PlayerStatsRaw(
                pl,
                row[PlayerStatsView.matchId].value,
                acts,
                act.filter {
                    it[PlayerStatsView.playerId]!!.value == pl && it[PlayerStatsView.action] == acts
                }.groupBy { it[PlayerStatsView.action] }.size.toLong(),
                row[PlayerStatsView.title],
            )
        }
        val players = actions.groupBy {
            it.playerId
        }.map { entry ->
            val player = playerService.getPlayerSimple(entry.key)
            var bScore: Long = 0
            val matchMap: EnumMap<MatchAction, Long> = EnumMap(MatchAction::class.java)
            MatchAction.entries.forEach { e ->
                matchMap[e] = 0
            }
            entry.value.forEach {
                matchMap[it.action] = matchMap[it.action]?.plus(it.count) ?: it.count
                if (it.action in listOf(MatchAction.GOAL, MatchAction.FINE)) {
                    bScore += it.count
                }
            }
            StatisticPlayerScore(player, matchMap, bScore)
        }
        return players.sortedBy { it.score }
    }

    fun getStatsPlayersFromSeason(title: String): List<StatisticPlayerScore?> {
        val act = PlayerStatsView.selectAll().where {
            PlayerStatsView.title eq title
        }.filterNotNull()


        val actions = act.map { row ->
            val pl=  row[PlayerStatsView.playerId]!!.value
            val acts = row[PlayerStatsView.action]
            PlayerStatsRaw(
                pl,
                row[PlayerStatsView.matchId].value,
                acts,
                act.filter {
                    it[PlayerStatsView.playerId]!!.value == pl && it[PlayerStatsView.action] == acts
                }.groupBy { it[PlayerStatsView.action] }.size.toLong(),
                row[PlayerStatsView.title],
            )
        }

        val players = actions.groupBy {
            it.playerId
        }.map { entry ->
            val player = playerService.getPlayerSimple(entry.key)
            var bScore: Long = 0
            val matchMap: EnumMap<MatchAction, Long> = EnumMap(MatchAction::class.java)
            MatchAction.entries.forEach { e ->
                matchMap[e] = 0
            }
            entry.value.forEach {
                matchMap[it.action] = matchMap[it.action]?.plus(it.count) ?: it.count
                if (it.action in listOf(MatchAction.GOAL, MatchAction.FINE)) {
                    bScore += it.count
                }
            }
            StatisticPlayerScore(player, matchMap, bScore)
        }
        return players.sortedBy { it.score }
    }

    override fun getStatisticSeason(season: String): StatisticSeason {
        val sId = seasonService.getSeasonFromTitle(season)
        val actionMap = mutableMapOf<MatchAction, ActionTeamEnemy>()

        MatchAction.entries.forEach { e ->
            actionMap[e] = ActionTeamEnemy(0, 0)
        }

        SeasonStatsView.selectAll().where {
            SeasonStatsView.title eq season
        }.map {
            actionMap[it[SeasonStatsView.action]] = ActionTeamEnemy(
                it[SeasonStatsView.team],
                it[SeasonStatsView.enemy],
            )
        }

        val statisticScore = MatchResultStatsView.selectAll().where {
            MatchResultStatsView.seasonId eq sId.id
        }

        val win = statisticScore.count { it[MatchResultStatsView.win] }
        val lose = statisticScore.count { it[MatchResultStatsView.lose] }
        val draw = statisticScore.count { it[MatchResultStatsView.draw] }

        val bestPlayer = getStatsPlayersFromSeason(season).ifEmpty { null }?.last()?.toStatisticPlayer()

        return StatisticSeason(win, lose, draw, actionMap, bestPlayer)

    }

    override fun getStatisticPlayerByMatch(playerId: Long, match: Long): StatisticPlayer? {
        return getStatsPlayersFromMatch(match).ifEmpty { null }
            ?.let { p -> p.first { it!!.player.id == playerId }?.toStatisticPlayer() }
    }

    override fun getStatisticPlayerBySeason(playerId: Long, season: String): StatisticPlayer? {
        return getStatsPlayersFromSeason(season).ifEmpty { null }
            ?.let { p -> p.first { it!!.player.id == playerId }?.toStatisticPlayer() }
    }


    override fun putManualTable(b: ManualTableDTO, s: String): ManualTableDTO {


        val season_id = seasonService.getSeasonFromTitle(s)
        ManualTable.deleteWhere {
            season eq season_id.id
        }
        ManualTable.insert {
            it[season] = season_id.id
            it[n] = b.n
            it[i] = b.i
            it[vo] = b.vo
            it[vb] = b.vb
            it[pb] = b.pb
            it[po] = b.po
            it[p] = b.p
            it[sh] = b.sh
            it[o] = b.o
            it[pero] = b.pero
            it[v] = b.v
        }
        return b
    }

    override fun getManualTable(s: String): ManualTableDTO {
        return ManualTable.selectAll().where { ManualTable.season eq seasonService.getSeasonFromTitle(s).id }
            .firstOrNull()?.let {
                ManualTableDTO(
                    it[ManualTable.n],
                    it[ManualTable.i],
                    it[ManualTable.v],
                    it[ManualTable.vo],
                    it[ManualTable.vb],
                    it[ManualTable.pb],
                    it[ManualTable.po],
                    it[ManualTable.p],
                    it[ManualTable.sh],
                    it[ManualTable.o],
                    it[ManualTable.pero],
                )
            } ?: ManualTableDTO(0, 0, 0, 0, 0, 0, 0, 0, "", 0, 0.0)
    }
}
