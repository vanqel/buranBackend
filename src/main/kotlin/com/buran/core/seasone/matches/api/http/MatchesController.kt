package com.buran.core.seasone.matches.api.http

import com.buran.core.seasone.RegAPI
import com.buran.core.seasone.matches.dto.MatchCreateInput
import com.buran.core.seasone.matches.dto.MatchOutput
import com.buran.core.seasone.matches.dto.MatchResult
import com.buran.core.seasone.matches.services.IMatchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class MatchesController(
    val service: IMatchService,
) {
    /**
     * Получить список матчей в сезоне
     */
    @GetMapping(RegAPI.MATCHES_SEASON)
    fun getAllMatches(
        @RequestParam season: String,
    ): ResponseEntity<List<MatchOutput?>> {
        return ResponseEntity.ok(service.getMatchesBySeason(season))
    }

    /**
     * Получить информацию о матче
     */
    @GetMapping("${RegAPI.MATCHES_SEASON}/{id_match}")
    fun getMatch(
        @PathVariable id_match: Long,
    ) = ResponseEntity.ok(service.getMatch(id_match))

    /**
     * Объявление нового матча
     */
    @PostMapping(RegAPI.MATCHES_SEASON)
    fun postMatch(
        @RequestParam season: String,
        @RequestBody body: MatchCreateInput,
    ) = ResponseEntity.ok(service.createMatch(season, body))

    /**
     * Удалить матч
     */
    @DeleteMapping("${RegAPI.MATCHES_SEASON}/{id_match}")
    fun delMatch(
        @PathVariable id_match: Long,
    ) = ResponseEntity.ok(service.deleteMatch(id_match))

    /**
     * Обновить информацию о матче
     */
    @PutMapping("${RegAPI.MATCHES_SEASON}/{id_match}")
    fun putMatch(
        @PathVariable id_match: Long,
        @RequestBody body: MatchCreateInput,
    ) = ResponseEntity.ok(service.updateMatch(id_match, body))

    /**
     * Добавить результаты матча
     */
    @PostMapping("${RegAPI.MATCHES_SEASON}/{id_match}/result")
    fun postMatchResult(
        @PathVariable id_match: Long,
        @RequestBody body: MatchResult,
    ) = ResponseEntity.ok(service.updateMatchResult(id_match, body))

    /**
     * Получить итоговую сводку матча
     */
    @GetMapping("${RegAPI.MATCHES_SEASON}/{id_match}/result")
    fun getMatchResult(
        @PathVariable id_match: Long,
    ) = ResponseEntity.ok(service.getMatchResults(id_match))
}
