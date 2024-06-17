package com.buran.core.seasone.statistic.repostiory

import com.buran.core.seasone.statistic.dto.ManualTableDTO
import com.buran.core.seasone.statistic.dto.StatisticMatch
import com.buran.core.seasone.statistic.dto.StatisticPlayer
import com.buran.core.seasone.statistic.dto.StatisticSeason

interface IStatisticRepository {
    fun getStatisticPlayersBySeason(
        season: String,
    ): List<StatisticPlayer?>

    fun getStatisticMatchBySeason(
        match: Long,
    ): StatisticMatch

    fun getStatisticSeason(
        season: String,
    ): StatisticSeason

    fun getStatisticPlayerByMatch(
        playerId: Long,
        match: Long,
    ): StatisticPlayer?

    fun getStatisticPlayerBySeason(
        playerId: Long,
        season: String,
    ): StatisticPlayer?

    fun putManualTable(b: ManualTableDTO): ManualTableDTO

    fun getManualTable(): ManualTableDTO
}
