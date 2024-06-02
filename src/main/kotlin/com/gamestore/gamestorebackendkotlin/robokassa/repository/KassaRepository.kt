package com.gamestore.gamestorebackendkotlin.robokassa.repository

import com.gamestore.gamestorebackendkotlin.auth.models.users.UserEntity
import com.gamestore.gamestorebackendkotlin.auth.models.users.table.UserTable
import com.gamestore.gamestorebackendkotlin.products.model.product.ProductEntity
import com.gamestore.gamestorebackendkotlin.robokassa.model.KassaEntity
import com.gamestore.gamestorebackendkotlin.robokassa.model.table.KassaTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class KassaRepository: IKassaRepository {
    override fun save(
        username: String,
        pid: Long,
        invID: Int,
    ) {
        KassaEntity.new {
            user = UserEntity.find { UserTable.username.eq(username) }.first().id
            product = ProductEntity.findById(pid)!!.id
            individalID = invID
        }
    }

    override fun succesPay(invID: Int) {
        KassaEntity.find { KassaTable.individualID eq invID }.first().let {
            it.status = true
        }
    }

    override fun errorPay(invID: Int) {
        KassaEntity.find { KassaTable.individualID eq invID }.first().let {
            it.status = false
        }
    }

    override fun findByUser(userEntity: EntityID<Long>): List<KassaEntity?> = KassaEntity.find { KassaTable.user eq userEntity }.toList()
}

