package com.buran.core.seasone.matches.services

import com.buran.core.seasone.matches.dto.MatchCreateInput
import com.buran.core.seasone.matches.dto.MatchOutput
import com.buran.core.seasone.matches.dto.MatchResult
import com.buran.core.seasone.matches.dto.MatchResultOutput

interface IMatchService {
    fun getMatchesBySeason(season: String): List<MatchOutput?>

    fun getMatch(id: Long): MatchOutput?

    fun createMatch(body: MatchCreateInput): MatchOutput

    fun updateMatch(id: Long, body: MatchCreateInput): MatchOutput

    fun deleteMatch(id: Long)

    fun updateMatchResult(id: Long, body: MatchResult): MatchOutput

    fun getMatchResults(id: Long): MatchResultOutput
}
