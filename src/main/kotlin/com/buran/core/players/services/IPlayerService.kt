package com.buran.core.players.services

import com.buran.core.players.dto.PlayerCreateInput
import com.buran.core.players.dto.PlayerFullOutput
import com.buran.core.players.dto.PlayerSimpleOutput
import com.buran.core.players.dto.PlayerUpdateInput

interface IPlayerService {
    fun createPlayer(body: PlayerCreateInput): PlayerFullOutput
    fun updatePlayer(body: PlayerUpdateInput): PlayerFullOutput
    fun archivePlayer(id: Long): Boolean
    fun getPlayer(id: Long): PlayerFullOutput
    fun getPlayers(id: List<Long>): List<PlayerFullOutput>
    fun getPlayerSimple(id: Long): PlayerSimpleOutput
    fun getPlayersSimple(id: List<Long>): List<PlayerSimpleOutput>

    fun getAllPlayers(): List<PlayerSimpleOutput>
}
