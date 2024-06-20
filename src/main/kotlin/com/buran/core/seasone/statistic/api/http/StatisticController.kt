package com.buran.core.seasone.statistic.api.http

import com.buran.core.seasone.RegAPI
import com.buran.core.seasone.statistic.dto.ManualTableDTO
import com.buran.core.seasone.statistic.dto.StatisticMatch
import com.buran.core.seasone.statistic.dto.StatisticPlayer
import com.buran.core.seasone.statistic.dto.StatisticSeason
import com.buran.core.seasone.statistic.repostiory.IStatisticRepository
import org.springframework.web.bind.annotation.*

@RestController
class StatisticController(
    val statisticRepository: IStatisticRepository,
) {
    @GetMapping("${RegAPI.STATISTIC_SEASON}/{playerId}")
    fun getStatisticPlayerBySeason(
        @PathVariable playerId: Long,
        @RequestParam season: String,
    ): StatisticPlayer? {
        return statisticRepository.getStatisticPlayerBySeason(playerId, season)
    }

    @GetMapping("${RegAPI.STATISTIC_SEASON}/{playerId}/{idMatch}")
    fun getStatisticPlayerByMatch(
        @PathVariable playerId: Long,
        @PathVariable idMatch: Long,
    ): StatisticPlayer? {
        return statisticRepository.getStatisticPlayerByMatch(playerId, idMatch)
    }

    @GetMapping(RegAPI.STATISTIC_SEASON)
    fun getStatisticSeason(
        @RequestParam season: String,
    ): StatisticSeason {
        return statisticRepository.getStatisticSeason(season)
    }

    @GetMapping("${RegAPI.STATISTIC_SEASON}/players")
    fun getStatisticPlayersBySeason(
        @RequestParam season: String,
    ): List<StatisticPlayer?> {
        return statisticRepository.getStatisticPlayersBySeason(season)
    }

    @GetMapping("${RegAPI.STATISTIC_SEASON}/match/{match_id}")
    fun getStatisticMatchBySeason(
        @PathVariable match_id: Long,
    ): StatisticMatch {
        return statisticRepository.getStatisticMatchBySeason(match_id)
    }

    @GetMapping("${RegAPI.STATISTIC_SEASON}/statsTable")
    fun getStatsManual(
        @RequestParam season: String,
    ) = statisticRepository.getManualTable(season)

    @PutMapping("${RegAPI.STATISTIC_SEASON}/statsTable")
    fun putStatsManual(@RequestBody stats: ManualTableDTO,
        @RequestParam season: String,
    ) = statisticRepository.putManualTable(stats,season)
}
