package com.gamestore.gamestorebackendkotlin.robokassa.repository

import com.gamestore.gamestorebackendkotlin.robokassa.model.KassaEntity
import org.jetbrains.exposed.dao.id.EntityID

interface IKassaRepository {
    fun save(
        username: String,
        pid: Long,
        invID: Int,
    )
    fun succesPay(invID: Int)
    fun errorPay(invID: Int)
    fun findByUser(userEntity: EntityID<Long>): List<KassaEntity?>
}