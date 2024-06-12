package com.buran.core.seasone.matches.repostiory

import com.buran.core.seasone.matches.dto.MatchCreateInput
import com.buran.core.seasone.matches.dto.MatchCreateOutput
import com.buran.core.seasone.matches.dto.MatchResult
import com.buran.core.seasone.matches.models.MatchResultEntity

interface IMatchRepository {

    fun getMatchesBySeason(seasonId: Long): List<MatchCreateOutput?>

    fun getMatch(id: Long): MatchCreateOutput?

    fun createMatch(season: Long,body: MatchCreateInput): MatchCreateOutput

    fun updateMatch(id: Long, body: MatchCreateInput): MatchCreateOutput

    fun deleteMatch(id: Long)

    fun updateMatchResult(id: Long, body: MatchResult):MatchCreateOutput

    fun getMatchResults(id: Long): List<MatchResultEntity?>
}
