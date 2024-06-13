package com.buran.core.seasone.matches.services

import com.buran.core.auth.errors.ValidationError
import com.buran.core.players.services.IPlayerService
import com.buran.core.seasone.core.services.ISeasonService
import com.buran.core.seasone.matches.dto.*
import com.buran.core.seasone.matches.enums.FinalResultMatch
import com.buran.core.seasone.matches.enums.MatchAction
import com.buran.core.seasone.matches.repostiory.IMatchRepository
import org.springframework.stereotype.Service

@Service
class MatchService(
    val repo: IMatchRepository,
    val playerService: IPlayerService,
    val seasonService: ISeasonService,
) : IMatchService {


    override fun getMatchesBySeason(season: String): List<MatchOutput?> {
        val matches = repo.getMatchesBySeason(seasonService.getSeasonFromTitle(season).id.value)
        val result = matches.filterNotNull().map { convertEntityToDTO(it) }
        return result
    }

    override fun getMatch(id: Long): MatchOutput? {
        val match = repo.getMatch(id)
        return convertEntityToDTO(match)
    }

    fun convertEntityToDTO(match: MatchCreateOutput?): MatchOutput? {
        val result = match?.let { matchCreateOutput ->
            MatchOutput(
                id = matchCreateOutput.id,
                enemy = matchCreateOutput.enemy,
                dateStart = matchCreateOutput.dateStart,
                team = playerService.getPlayersSimple(matchCreateOutput.team),
                title = matchCreateOutput.title,
                actions = repo.getMatchResults(matchCreateOutput.id).filterNotNull().map {
                    MatchResult(
                        playerId = it.playerId?.value,
                        minutes = it.minutes,
                        action = it.action,
                        enemy = it.enemy
                    )
                }.ifEmpty { listOf() }
            )
        }
        return result
    }

    override fun createMatch(season: String, body: MatchCreateInput): MatchOutput {
        val match = repo.createMatch(
            seasonService.getSeasonFromTitle(season).id.value, body
        )
        return convertEntityToDTO(match)!!
    }

    override fun updateMatch(id: Long, body: MatchCreateInput): MatchOutput {
        val match = repo.updateMatch(id, body)
        return convertEntityToDTO(match)!!
    }

    override fun deleteMatch(id: Long) {
        repo.deleteMatch(id)
    }

    override fun updateMatchResult(id: Long, body: MatchResult): MatchOutput {
        val match = repo.updateMatchResult(id, body)
        return convertEntityToDTO(match)!!
    }

    override fun getMatchResults(id: Long): MatchResultOutput {
        val match = repo.getMatchResults(id).map { it?.toDto() }
        val enemyScore = match.filterNotNull().filter { it.enemy && it.action == MatchAction.GOAL }.size
        val teamScore = match.filterNotNull().filter { !it.enemy && it.action == MatchAction.GOAL }.size
        return MatchResultOutput(
            enemyScore = enemyScore,
            teamScore = teamScore,
            result = if (teamScore > enemyScore) FinalResultMatch.WIN
            else if (enemyScore > teamScore) FinalResultMatch.LOSE
            else FinalResultMatch.DRAW
        )
    }
}
