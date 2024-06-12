package com.buran.core.players.services

import com.buran.core.auth.errors.ValidationError
import com.buran.core.players.dto.PlayerCreateInput
import com.buran.core.players.dto.PlayerFullOutput
import com.buran.core.players.dto.PlayerSimpleOutput
import com.buran.core.players.dto.PlayerUpdateInput
import com.buran.core.players.repostiory.IPlayerRepository
import com.buran.core.storage.core.service.MinioService
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlayerService(
    val repo: IPlayerRepository,
    val minioService: MinioService,
) : IPlayerService {

    override fun createPlayer(body: PlayerCreateInput): PlayerFullOutput {
        return repo.createPlayer(body).toFullOutput(minioService.getObject(UUID.fromString(body.photo))?.url)
    }

    override fun updatePlayer(body: PlayerUpdateInput): PlayerFullOutput {
        return repo.updatePlayer(body).toFullOutput(
            body.photo?.let {
                minioService.getObject(it)?.url
            }
        )
    }

    override fun archivePlayer(id: Long): Boolean {
        return repo.deletePlayer(id)
    }

    override fun getPlayer(id: Long): PlayerFullOutput {
        val ent = repo.getPlayer(id)
        val url = ent?.photo?.let {
            minioService.getObject(it)?.url
        }
        return ent?.toFullOutput(url) ?: throw ValidationError("Игрок не найден")
    }

    override fun getPlayers(id: List<Long>): List<PlayerFullOutput> {
        val ent = repo.getPlayers(id)
        val result = ent.map{ playerEntity ->
            val url = playerEntity.photo?.let {
                minioService.getObject(it)?.url
            }
            playerEntity.toFullOutput(url)
        }
        return result.ifEmpty { throw ValidationError("Игроки не найдены") }
    }

    override fun getPlayerSimple(id: Long): PlayerSimpleOutput {
        val ent = repo.getPlayer(id)
        val url = ent?.photo?.let {
            minioService.getObject(it)?.url
        }
        return ent?.toSimpleOutput(url) ?: throw ValidationError("Игрок не найден")

    }

    override fun getPlayersSimple(id: List<Long>): List<PlayerSimpleOutput> {
        val ent = repo.getPlayers(id)
        val result = ent.map{ playerEntity ->
            val url = playerEntity.photo?.let {
                minioService.getObject(it)?.url
            }
            playerEntity.toSimpleOutput(url)
        }
        return result.ifEmpty { throw ValidationError("Игроки не найдены") }
    }

    override fun getAllPlayers(): List<PlayerSimpleOutput> {
        val ent = repo.getAllPlayers()
        val result = ent.map{ playerEntity ->
            val url = playerEntity.photo?.let {
                minioService.getObject(it)?.url
            }
            playerEntity.toSimpleOutput(url)
        }
        return result.ifEmpty { throw ValidationError("Игроков не найдено") }
    }

    override fun getAllPlayersArchived(): List<PlayerSimpleOutput> {
        val ent = repo.getAllPlayersArchived()
        val result = ent.map{ playerEntity ->
            val url = playerEntity.photo?.let {
                minioService.getObject(it)?.url
            }
            playerEntity.toSimpleOutput(url)
        }
        return result.ifEmpty { throw ValidationError("Игроков не найдено") }
    }
}
