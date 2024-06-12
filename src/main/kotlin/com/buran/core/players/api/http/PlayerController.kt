package com.buran.core.players.api.http

import com.buran.core.players.dto.PlayerCreateInput
import com.buran.core.players.dto.PlayerFullOutput
import com.buran.core.players.dto.PlayerSimpleOutput
import com.buran.core.players.dto.PlayerUpdateInput
import com.buran.core.players.services.IPlayerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/players")
class PlayerController(
    val service: IPlayerService,
) {
    /**
     * Создание игрока
     */
    @PostMapping
    fun createPlayer(
        @RequestBody body: PlayerCreateInput,
    ): ResponseEntity<PlayerFullOutput> {
        return ResponseEntity.ok(service.createPlayer(body))
    }

    /**
     * Обновление игрока
     */
    @PutMapping
    fun updatePlayer(
        @RequestBody body: PlayerUpdateInput,
    ): ResponseEntity<PlayerFullOutput> {
        return ResponseEntity.ok(service.updatePlayer(body))
    }

    /**
     * Удаление игрока
     */
    @DeleteMapping("{playerId}")
    fun archivePlayer(
        @PathVariable playerId: Long,
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(service.archivePlayer(playerId))
    }

    /**
     * Поиск игрока
     */
    @GetMapping("{playerId}")
    fun getPlayer(
        @PathVariable playerId: Long,
    ): ResponseEntity<PlayerFullOutput> {
        return ResponseEntity.ok(service.getPlayer(playerId))
    }

    /**
     * Поиск игрока, сокращённый вариант
     */
    @GetMapping("/s/{playerId}")
    fun getPlayerShort(
        @PathVariable playerId: Long,
    ): ResponseEntity<PlayerSimpleOutput> {
        return ResponseEntity.ok(service.getPlayerSimple(playerId))
    }

    /**
     * Поиск всех игроков
     */
    @GetMapping
    fun getPlayers(): ResponseEntity<List<PlayerSimpleOutput>> {
        return ResponseEntity.ok(service.getAllPlayers())
    }

}
