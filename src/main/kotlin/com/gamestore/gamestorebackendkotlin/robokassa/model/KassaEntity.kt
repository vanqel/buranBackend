package com.gamestore.gamestorebackendkotlin.balance.model.history

import com.gamestore.gamestorebackendkotlin.config.ExtendedLongEntity
import com.gamestore.gamestorebackendkotlin.auth.models.users.table.UserTable
import com.gamestore.gamestorebackendkotlin.balance.dto.OperationOutputDTO
import com.gamestore.gamestorebackendkotlin.balance.model.history.table.HistoryOperation
import com.gamestore.gamestorebackendkotlin.products.model.product.ProductEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class OperationEntity(id: EntityID<Long>) : ExtendedLongEntity(id, UserTable) {
    companion object : LongEntityClass<OperationEntity>(HistoryOperation)

    var balance by HistoryOperation.balance
    var productOperation by HistoryOperation.product_operation

    fun toDTO(): OperationOutputDTO? =
        ProductEntity.findById(productOperation)?.let {
            OperationOutputDTO(it.toSimpleDTO(), true)
        }
}
