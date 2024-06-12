package com.buran.core.seasone.statistic.api.http

import com.buran.core.seasone.RegAPI
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class StatisticController {
    @GetMapping("${RegAPI.STATISTIC_SEASON}/{playerId}")
    fun getStatisticPlayerBySeason(
        @PathVariable playerId: String,
        @PathVariable season: String
    ): Any? {
        return null
    }

    @GetMapping("${RegAPI.STATISTIC_SEASON}/{playerId}/{id_match}")
    fun getStatisticPlayerByMatch(
        @PathVariable playerId: String,
        @PathVariable season: String,
        @PathVariable id_match: String
    ): Any? {
        return null
    }

    @GetMapping(RegAPI.STATISTIC_SEASON)
    fun getStatisticSeason(
        @PathVariable season: String
    ): Any? {
        return null
    }

    @GetMapping("${RegAPI.STATISTIC_SEASON}/players")
    fun getStatisticPlayersBySeason(
        @PathVariable season: String,
    ): Any? {
        return null
    }

}
