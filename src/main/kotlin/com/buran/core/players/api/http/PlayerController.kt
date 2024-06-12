package com.buran.core.players.api.http

import com.buran.core.players.dto.PlayerCreateInput
import com.buran.core.players.dto.PlayerFullOutput
import com.buran.core.players.dto.PlayerSimpleOutput
import com.buran.core.players.dto.PlayerUpdateInput
import com.buran.core.players.services.IPlayerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/*
{
  "name": "BOBI HENDRICKSON",
  "number": 1,
  "biography": "Боби Хендриксон родился в маленькой деревне вдали от больших городов. С детства он был увлечен хоккеем и мечтал стать профессиональным игроком. Он начал тренироваться с раннего возраста и быстро прогрессировал, пока не стал одним из лучших молодых игроков в своей команде.\n\nОднако, в возрасте 18 лет, Боби попал в автомобильную аварию, которая потребовала ему много времени на восстановление. В это время, его команда потеряла многих своих лучших игроков, и Боби был вынужден стать одним из ключевых игроков.\n\nХотя он был известен своими шутками и необычными трюками на льду, Боби Хендриксон был также и тем, кто не брезгал перед своими действиями. Он был известен своей хулиганской природой и не стеснялся злоупотреблять каждую возможность, чтобы сделать шутку или устроить драку на льду.\n\nОднажды, в финале кубка чемпионов, когда счет был вничью и оставалось всего несколько секунд до конца матча, Хендриксон решил устроить шутку. Вместо того, чтобы бить по воротам противника, он вдруг остановился, достал из кармана свой подгузник, сделал вид, что он мяч, и забил его в ворота, заставив всех в истерике смеяться. Этот необычный момент стал легендой, и Хендриксон получил прозвище \"Мастер Шуток на Льду\".\n\nХотя его карьера была полна неожиданных моментов, Хендриксон всегда оставался человеком, который не стеснялся быть собой и делать шутки, даже на хоккейном поле. Его необычный стиль и чувtos humor сделали его любимым игроком и легендой в мире хоккея.",
  "photo": "c0b7d7f0-ce07-481e-9ee0-3ee45ed0dfca",
  "birthDate": "2024-06-12",
  "type": "GOALKEEPER"
}
 */
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
     * Поиск всех игроков
     */
    @GetMapping
    fun getPlayers(): ResponseEntity<List<PlayerSimpleOutput>> {
        return ResponseEntity.ok(service.getAllPlayers())
    }

    /**
     * Поиск всех игроков
     */
    @GetMapping("archived")
    fun getPlayersArchived(): ResponseEntity<List<PlayerSimpleOutput>> {
        return ResponseEntity.ok(service.getAllPlayersArchived())
    }
}
