package com.buran.core.players.repostiory

import com.buran.core.players.dto.PlayerCreateInput
import com.buran.core.players.dto.PlayerUpdateInput
import com.buran.core.players.models.PlayerEntity

interface IPlayerRepository {
    fun createPlayer(body: PlayerCreateInput): PlayerEntity
    fun updatePlayer(body: PlayerUpdateInput): PlayerEntity
    fun deletePlayer(id: Long): Boolean
    fun getPlayer(id: Long): PlayerEntity?
    fun getAllPlayers(): List<PlayerEntity>
    fun getAllPlayersArchived(): List<PlayerEntity>
    fun getPlayers(id: List<Long>): List<PlayerEntity>
}
